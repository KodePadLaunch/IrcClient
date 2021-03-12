package com.kodepad.irc.handler

import com.kodepad.irc.TestConstants.HANDLER_EXECUTION_TIMEOUT_IN_MILLIS
import com.kodepad.irc.connection.Connection
import com.kodepad.irc.event.EventListener
import com.kodepad.irc.logging.Markers.TEST_FLOW
import com.kodepad.irc.message.Message
import com.kodepad.irc.network.NetworkImpl
import com.kodepad.irc.network.NetworkState
import com.kodepad.irc.vo.User
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.job
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory

class MessageHandlerUnitTest {
    companion object {
        private val logger = LoggerFactory.getLogger(MessageHandlerUnitTest::class.java)
    }

    @Test(timeout = HANDLER_EXECUTION_TIMEOUT_IN_MILLIS)
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

        val networkState = NetworkState(
            User(
                "testNickname",
                "testUsername",
                "testRealname",
            ),
        )

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

            override fun close() {
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

        val commandHandlerFactory = CommandHandlerFactory(mockConnection, networkState, null, null)
        val messageHandler = MessageHandler(
            commandHandlerFactory,
            rawMessageEventListener,
        )

        val network = NetworkImpl(
            networkState,
            mockConnection,
            messageHandler,
            coroutineScope
        )

        runBlocking {
            coroutineScope.coroutineContext.job.join()
        }

        assert(testFlag)
    }
}