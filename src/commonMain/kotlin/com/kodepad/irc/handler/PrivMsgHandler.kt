package com.kodepad.irc.handler

import com.kodepad.irc.Message
import com.kodepad.irc.event.EventDispatcher
import com.kodepad.irc.event.PrivMsgEvent
import com.kodepad.irc.logging.LoggerFactory

class PrivMsgHandler(private val eventDispatcher: EventDispatcher) : Handler {
    companion object {
        private val logger = LoggerFactory.getLogger(PrivMsgHandler::class)
    }

    override suspend fun onMessage(message: Message) {
        logger.debug("message: {}", message)

        val privMsg = PrivMsgEvent(message)
        eventDispatcher.dispatch(PrivMsgEvent::class, privMsg)
    }
}