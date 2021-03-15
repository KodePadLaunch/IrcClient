package com.kodepad.irc.handler

import com.kodepad.irc.event.EventDispatcher
import com.kodepad.irc.logging.LoggerFactory
import com.kodepad.irc.Message
import com.kodepad.irc.event.NoticeEvent

class NoticeHandler(private val eventDispatcher: EventDispatcher) : Handler {
    companion object {
        private val logger = LoggerFactory.getLogger(NoticeHandler::class)
    }

    override suspend fun onMessage(message: Message) {
        logger.debug("message: {}", message)


        val notice = NoticeEvent(message)
        eventDispatcher.dispatch(NoticeEvent::class, notice)
    }
}