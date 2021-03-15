package com.kodepad.irc.handler

import com.kodepad.irc.event.EventDispatcher
import com.kodepad.irc.logging.LoggerFactory
import com.kodepad.irc.message.Message
import com.kodepad.irc.message.client.sending.PrivMsg

class PrivMsgHandler(private val eventDispatcher: EventDispatcher) : Handler {
    companion object {
        private val logger = LoggerFactory.getLogger(PrivMsgHandler::class)
    }

    override suspend fun onMessage(message: Message) {
        logger.debug("message: {}", message)

        val privMsg = PrivMsg(message)
        eventDispatcher.dispatch(PrivMsg::class, privMsg)
    }
}