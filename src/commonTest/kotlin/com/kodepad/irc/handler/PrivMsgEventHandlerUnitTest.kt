package com.kodepad.irc.handler

import com.kodepad.irc.Message
import com.kodepad.irc.NetworkState
import com.kodepad.irc.command.NickCommand
import com.kodepad.irc.command.UserCommand
import com.kodepad.irc.connection.Connection
import com.kodepad.irc.event.EventDispatcherImpl
import com.kodepad.irc.event.EventListener
import com.kodepad.irc.event.PrivMsgEvent
import com.kodepad.irc.logging.LoggerFactory
import com.kodepad.irc.logging.Markers.TEST_FLOW
import com.kodepad.irc.network.NetworkImpl
import com.kodepad.kotlinx.coroutines.runBlockingTest
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.job

class PrivMsgEventHandlerUnitTest {
    companion object {
        private val logger = LoggerFactory.getLogger(PrivMsgEventHandlerUnitTest::class)
    }

    @Test(/* timeout = HANDLER_EXECUTION_TIMEOUT_IN_MILLIS */)
    fun `privMsg handler invokation success`() {
        val coroutineScope = CoroutineScope(EmptyCoroutineContext)

        val privMsg = Message(
            tags=null,
            source="MidKnightKoder!677998db@103.121.152.219",
            command="PRIVMSG",
            parameters=listOf(
                "#ircclienttest",
                "hello, world"
            )
        )

        val networkState = NetworkState()

        val mockConnection = object : Connection {
            override suspend fun connect() {
                logger.debug(TEST_FLOW, "connect called!")
            }

            override suspend fun read(): Message {
                return privMsg
            }

            override suspend fun write(message: Message) {
                logger.debug(TEST_FLOW, "message: $message")
            }

            override suspend fun close() {
                logger.debug(TEST_FLOW, "close called!")
            }
        }

        var testFlag = false

        val privMsgEventListener = object : EventListener<PrivMsgEvent> {
            override fun onEvent(event: PrivMsgEvent) {
                testFlag = true
                logger.debug(TEST_FLOW, "event: $event")
                assertEquals(privMsg, event.message)
                coroutineScope.cancel()
            }
        }

        val eventDispatcher = EventDispatcherImpl()

        val commandHandlerFactory = CommandHandlerFactory(
            mockConnection,
            networkState,
            eventDispatcher
        )
        val messageHandler = MessageHandler(commandHandlerFactory, eventDispatcher)

        // todo: This is kind of integration test move it into a separate module
        val network = NetworkImpl(
            networkState,
            mockConnection,
            messageHandler,
            eventDispatcher,
            coroutineScope,
        )
        network.addEventListener(PrivMsgEvent::class, privMsgEventListener)

        val nickCommand = NickCommand(
            "testNickname",
            )
        val userCommand = UserCommand(
            "testUsername",
            "testRealname",
            )

        runBlockingTest {
            network.connectAndRegister(nickCommand, userCommand)
            coroutineScope.coroutineContext.job.join()
        }

        assertTrue(testFlag)
    }

    @Test(/* timeout = HANDLER_EXECUTION_TIMEOUT_IN_MILLIS */)
    fun `notice handler non invocation on different command`() {
        val coroutineScope = CoroutineScope(EmptyCoroutineContext)

        val noticeMessage = Message(
            tags=null,
            source="freenode-connect!~eir@freenode/utility-bot/frigg",
            command="NOTICE",
            parameters= listOf(
                "dummykodepadnick",
                "Welcome to freenode. To protect the network all new connections will be scanned for vulnerabilities. This will not harm your computer, and vulnerable hosts will be notified."
            )
        )

        val networkState = NetworkState()

        val mockConnection = object : Connection {
            private var toggle = true

            override suspend fun connect() {
                logger.debug(TEST_FLOW, "connect called!")
            }

            override suspend fun read(): Message {
                if(toggle) {
                    toggle = false
                }
                else {
                    coroutineScope.cancel()
                }

                return noticeMessage
            }

            override suspend fun write(message: Message) {
                logger.debug(TEST_FLOW, "message: $message")
            }

            override suspend fun close() {
                logger.debug(TEST_FLOW, "close called!")
            }
        }

        var testFlag = true

        val privMsgEventListener = object : EventListener<PrivMsgEvent> {
            override fun onEvent(event: PrivMsgEvent) {
                testFlag = false
                logger.debug(TEST_FLOW, "event: $event")
            }
        }

        val eventDispatcher = EventDispatcherImpl()

        val commandHandlerFactory = CommandHandlerFactory(
            mockConnection,
            networkState,
            eventDispatcher,
        )
        val messageHandler = MessageHandler(commandHandlerFactory, eventDispatcher)

        val network = NetworkImpl(
            networkState,
            mockConnection,
            messageHandler,
            eventDispatcher,
            coroutineScope,
        )
        network.addEventListener(PrivMsgEvent::class, privMsgEventListener)

        val nickCommand = NickCommand(
            "testNickname",
            )
        val userCommand = UserCommand(
            "testUsername",
            "testRealname",
            )

        runBlockingTest {
            network.connectAndRegister(nickCommand, userCommand)
            coroutineScope.coroutineContext.job.join()
        }

        assertTrue(testFlag)
    }
}