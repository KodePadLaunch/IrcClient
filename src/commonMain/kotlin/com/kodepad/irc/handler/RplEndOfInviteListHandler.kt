package com.kodepad.irc.handler

import com.kodepad.irc.message.Message
import com.kodepad.irc.logging.LoggerFactory

class RplEndOfInviteListHandler() : Handler {
    companion object {
        private val logger = LoggerFactory.getLogger(RplEndOfInviteListHandler::class)
    }

      override suspend fun onMessage(message: Message) {
        logger.debug("message: {}", message)
        
        logger.warn("Handler has no Operations!!")
    }
}