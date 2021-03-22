package com.kodepad.irc.handler

import com.kodepad.irc.Message
import com.kodepad.irc.command.NickCommand
import com.kodepad.irc.command.UserCommand
import com.kodepad.irc.connection.Connection
import com.kodepad.irc.event.EventDispatcherImpl
import com.kodepad.irc.event.EventListener
import com.kodepad.irc.logging.LoggerFactory
import com.kodepad.irc.logging.Markers.TEST_FLOW
import com.kodepad.irc.network.NetworkImpl
import com.kodepad.irc.state.MutableNetworkState
import com.kodepad.kotlinx.coroutines.runBlockingTest
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.job

class MessageHandlerUnitTest {
    companion object {
        private val logger = LoggerFactory.getLogger(MessageHandlerUnitTest::class)
    }

    @Test(/* timeout = HANDLER_EXECUTION_TIMEOUT_IN_MILLIS */)
    fun `message handler invokation success`() {
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

        val mutableNetworkState = MutableNetworkState()

        val mockConnection = object : Connection {
            override suspend fun connect() {
                logger.debug(TEST_FLOW, "connect called!")
            }

            override suspend fun read(): Message {
                return noticeMessage
            }

            override suspend fun write(message: Message) {
                logger.debug(TEST_FLOW, "message: $message")
            }

            override suspend fun close() {
                logger.debug(TEST_FLOW, "close called!")
            }
        }

        var testFlag = false

        val rawMessageEventListener = object : EventListener<Message> {
            override fun onEvent(event: Message) {
                testFlag = true
                logger.debug(TEST_FLOW, "event: $event")
                assertEquals(noticeMessage, event)
                coroutineScope.cancel()
            }
        }

        val eventDispatcher = EventDispatcherImpl()

        val commandHandlerFactory = CommandHandlerFactory(mockConnection, mutableNetworkState, eventDispatcher)
        val messageHandler = MessageHandler(
            commandHandlerFactory,
            eventDispatcher
        )

        val network = NetworkImpl(
            mutableNetworkState,
            mockConnection,
            messageHandler,
            eventDispatcher,
            coroutineScope,
        )

        network.registerEventListener(Message::class, rawMessageEventListener)

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