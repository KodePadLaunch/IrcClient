package com.kodepad.irc.handler

import com.kodepad.irc.message.Message
import com.kodepad.irc.logging.LoggerFactory

class RplGlobalUsersHandler() : Handler {
    companion object {
        private val logger = LoggerFactory.getLogger(RplGlobalUsersHandler::class)
    }

      override suspend fun onMessage(message: Message) {
        logger.debug("message: {}", message)
        
        logger.warn("Handler has no Operations!!")
    }
}