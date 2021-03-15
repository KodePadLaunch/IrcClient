package com.kodepad.irc.event

import com.kodepad.irc.Message
import com.kodepad.irc.exception.IrcClientTestException
import com.kodepad.irc.logging.LoggerFactory
import com.kodepad.irc.logging.Markers.TEST_FLOW
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
            NoticeEvent::class,
            object : EventListener<NoticeEvent> {
                override fun onEvent(event: NoticeEvent) {
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
        val notice = NoticeEvent(
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
            NoticeEvent::class,
            object : EventListener<NoticeEvent> {
                override fun onEvent(event: NoticeEvent) {
                    logger.debug(TEST_FLOW, "Notice Event Listener called!")
                    logger.debug(TEST_FLOW, "event: $event")
                    assertEquals(notice, event)
                }
            }
        )

        eventDispatcher.dispatch(NoticeEvent::class, notice)
    }
}
