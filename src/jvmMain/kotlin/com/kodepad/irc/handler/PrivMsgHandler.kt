package com.kodepad.irc.handler

import com.kodepad.irc.event.EventListener
import com.kodepad.irc.message.Message
import com.kodepad.irc.message.client.sending.PrivMsg
import org.slf4j.LoggerFactory

class PrivMsgHandler(private val privMsgEventListener: EventListener<PrivMsg>?) : Handler {
    companion object {
        private val logger = LoggerFactory.getLogger(PrivMsgHandler::class.java)
    }

    override suspend fun onMessage(message: Message) {
        logger.debug("message: {}", message)

        val privMsg = PrivMsg(message)
        privMsgEventListener?.onEvent(privMsg)
    }
}