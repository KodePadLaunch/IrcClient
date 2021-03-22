package com.kodepad.irc.state

import com.kodepad.irc.Message
import com.kodepad.irc.command.NickCommand
import com.kodepad.irc.command.UserCommand
import com.kodepad.irc.connection.Connection
import com.kodepad.irc.connection.ConnectionExceptionCaptureDecorator
import com.kodepad.irc.event.EventDispatcherImpl
import com.kodepad.irc.exception.socket.FailedToReadException
import com.kodepad.irc.handler.CommandHandlerFactory
import com.kodepad.irc.handler.MessageHandler
import com.kodepad.irc.logging.LoggerFactory
import com.kodepad.irc.logging.Markers.TEST_FLOW
import com.kodepad.irc.network.NetworkImpl
import com.kodepad.kotlinx.coroutines.runBlockingTest
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.job
import kotlinx.coroutines.launch

class NetworkLifecycleUnitTest {
    companion object {
        private val logger = LoggerFactory.getLogger(NetworkLifecycleUnitTest::class)
    }

    @Test
    fun `network lifecycle states are moved properly`() {
        val eventDispatcher = EventDispatcherImpl()
        val mutableNetworkState = MutableNetworkState()

        val mockConnection = object: Connection {
            override suspend fun connect() {}
            override suspend fun read(): Message {
                return Message(
                    tags=null,
                    source="MidKnightKoder!677998db@103.121.152.219",
                    command="PRIVMSG",
                    parameters=listOf(
                        "#ircclienttest",
                        "hello, world!"
                    )
                )
            }
            override suspend fun write(message: Message) {}
            override suspend fun close() {}
        }

        val connection = ConnectionExceptionCaptureDecorator(mockConnection, eventDispatcher, mutableNetworkState)

        val commandHandlerFactory = CommandHandlerFactory(
            connection,
            mutableNetworkState,
            eventDispatcher,
        )
        val messageHandler = MessageHandler(
            commandHandlerFactory,
            eventDispatcher,
        )

        val network = NetworkImpl(
            mutableNetworkState,
            connection,
            messageHandler,
            eventDispatcher,
            )


        val networkState = network.getNetworkState()

        var stateCount = 0
        runBlockingTest {
            var coroutineScope = CoroutineScope(EmptyCoroutineContext)
            coroutineScope.launch {
                networkState.networkLifecycle.collect { networkLifecycle ->
                    logger.debug(TEST_FLOW, "networkLifecycle: $networkLifecycle")
                    assertEquals(NetworkLifecycle.READY, networkLifecycle)
                    stateCount++
                    coroutineScope.cancel()
                }
            }.join()

            network.connectAndRegister(
                NickCommand(
                    "testnickname1"
                ),
                UserCommand(
                    "testusername1",
                    "IRC Client Test Host"
                )
            )
            coroutineScope = CoroutineScope(EmptyCoroutineContext)
            coroutineScope.launch {
                networkState.networkLifecycle.collect { networkLifecycle ->
                    logger.debug(TEST_FLOW, "networkLifecycle: $networkLifecycle")
                    assertEquals(NetworkLifecycle.REGISTERED, networkLifecycle)
                    stateCount++
                    coroutineScope.cancel()
                }
            }.join()

            network.close()
            coroutineScope = CoroutineScope(EmptyCoroutineContext)
            coroutineScope.launch {
                networkState.networkLifecycle.collect { networkLifecycle ->
                    logger.debug(TEST_FLOW, "networkLifecycle: $networkLifecycle")
                    assertEquals(NetworkLifecycle.CLOSED, networkLifecycle)
                    stateCount++
                    coroutineScope.cancel()
                }
            }.join()
        }

        assertEquals(3, stateCount)
    }

    @Test
    fun `NetworkLifecycle state should move to closed on SocketException`() {
        val eventDispatcher = EventDispatcherImpl()
        val mutableNetworkState = MutableNetworkState()

        val mockConnection = object: Connection {
            override suspend fun connect() {}

            override suspend fun read(): Message {
                throw FailedToReadException("Failed to read!!")
            }

            override suspend fun write(message: Message) {}

            override suspend fun close() {}
        }

        val connection = ConnectionExceptionCaptureDecorator(mockConnection, eventDispatcher, mutableNetworkState)

        val commandHandlerFactory = CommandHandlerFactory(
            connection,
            mutableNetworkState,
            eventDispatcher,
        )
        val messageHandler = MessageHandler(
            commandHandlerFactory,
            eventDispatcher,
        )

        val coroutineScope = CoroutineScope(EmptyCoroutineContext)
        val network = NetworkImpl(
            mutableNetworkState,
            connection,
            messageHandler,
            eventDispatcher,
            coroutineScope,
        )

        val networkState = network.getNetworkState()

        var stateCount = 0
        runBlockingTest {
            var testCoroutineScope = CoroutineScope(EmptyCoroutineContext)
            testCoroutineScope.launch {
                networkState.networkLifecycle.collect { networkLifecycle ->
                    logger.debug(TEST_FLOW, "networkLifecycle: $networkLifecycle")
                    assertEquals(NetworkLifecycle.READY, networkLifecycle)
                    stateCount++
                    testCoroutineScope.cancel()
                }
            }.join()

            network.connectAndRegister(
                NickCommand(
                    "testnickname1"
                ),
                UserCommand(
                    "testusername1",
                    "IRC Client Test Host"
                )
            )
            coroutineScope.coroutineContext.job.join()

            testCoroutineScope = CoroutineScope(EmptyCoroutineContext)
            testCoroutineScope.launch {
                networkState.networkLifecycle.collect { networkLifecycle ->
                    logger.debug(TEST_FLOW, "networkLifecycle: $networkLifecycle")
                    assertEquals(NetworkLifecycle.CLOSED, networkLifecycle)
                    stateCount++
                    testCoroutineScope.cancel()
                }
            }.join()
        }

        assertEquals(2, stateCount)
    }
}