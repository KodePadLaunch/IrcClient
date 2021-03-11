package com.kodepad.irc.handler

import com.kodepad.irc.event.EventListener
import com.kodepad.irc.message.Message
import com.kodepad.irc.message.client.sending.Notice
import org.slf4j.LoggerFactory

class NoticeHandler(private val noticeEventListener: EventListener<Notice>?) : Handler {
    companion object {
        private val logger = LoggerFactory.getLogger(NoticeHandler::class.java)
    }

    override suspend fun onMessage(message: Message) {
        logger.debug("message: {}", message)


        val notice = Notice(message)
        noticeEventListener?.onEvent(notice)
    }
}