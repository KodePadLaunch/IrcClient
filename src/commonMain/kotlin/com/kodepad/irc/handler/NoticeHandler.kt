package com.kodepad.irc.handler

import com.kodepad.irc.event.EventDispatcher
import com.kodepad.irc.message.Message
import com.kodepad.irc.message.client.sending.Notice
import com.kodepad.irc.logging.LoggerFactory

class NoticeHandler(private val eventDispatcher: EventDispatcher) : Handler {
    companion object {
        private val logger = LoggerFactory.getLogger(NoticeHandler::class)
    }

    override suspend fun onMessage(message: Message) {
        logger.debug("message: {}", message)


        val notice = Notice(message)
        eventDispatcher.dispatch(Notice::class, notice)
    }
}