package com.kodepad.irc.event

import com.kodepad.irc.exception.IrcClientTestException
import com.kodepad.irc.logging.LoggerFactory
import com.kodepad.irc.logging.Markers.TEST_FLOW
import com.kodepad.irc.message.Message
import com.kodepad.irc.message.client.sending.Notice
import kotlin.test.Test
import kotlin.test.assertEquals

class EventDispatcherImplUnitTest {
    companion object {
        private val logger = LoggerFactory.getLogger(EventDispatcherImplUnitTest::class)
    }

    @Test
    fun `test and trigger message listener`() {
        val noticeMessage = Message(
            tags = null,
            source = "freenode-connect!~eir@freenode/utility-bot/frigg",
            command = "NOTICE",
            parameters = listOf(
                "dummykodepadnick",
                "Welcome to freenode. To protect the network all new connections will be scanned for vulnerabilities. This will not harm your computer, and vulnerable hosts will be notified."
            )
        )

        val eventDispatcher = EventDispatcherImpl()
        eventDispatcher.addListener(
            Message::class,
            object : EventListener<Message> {
                override fun onEvent(event: Message) {
                    logger.debug(TEST_FLOW, "Message Event Listener called!")
                    logger.debug(TEST_FLOW, "event: $event")
                    assertEquals(noticeMessage, event)
                }
            }
        )
        eventDispatcher.addListener(
            Notice::class,
            object : EventListener<Notice> {
                override fun onEvent(event: Notice) {
                    logger.debug(TEST_FLOW, "Notice Event Listener called!")
                    logger.debug(TEST_FLOW, "event: $event")
                    throw IrcClientTestException("Notice Event Listener should not be called")
                }
            }
        )

        eventDispatcher.dispatch(Message::class, noticeMessage)
    }

    @Test
    fun `test and trigger notice listener`() {
        val notice = Notice(
            Message(
                tags = null,
                source = "freenode-connect!~eir@freenode/utility-bot/frigg",
                command = "NOTICE",
                parameters = listOf(
                    "dummykodepadnick",
                    "Welcome to freenode. To protect the network all new connections will be scanned for vulnerabilities. This will not harm your computer, and vulnerable hosts will be notified."
                )
            )
        )

        val eventDispatcher = EventDispatcherImpl()
        eventDispatcher.addListener(
            Message::class,
            object : EventListener<Message> {
                override fun onEvent(event: Message) {
                    logger.debug(TEST_FLOW, "Message Event Listener called!")
                    logger.debug(TEST_FLOW, "event: $event")
                    throw IrcClientTestException("Notice Event Listener should not be called")
                }
            }
        )
        eventDispatcher.addListener(
            Notice::class,
            object : EventListener<Notice> {
                override fun onEvent(event: Notice) {
                    logger.debug(TEST_FLOW, "Notice Event Listener called!")
                    logger.debug(TEST_FLOW, "event: $event")
                    assertEquals(notice, event)
                }
            }
        )

        eventDispatcher.dispatch(Notice::class, notice)
    }
}
