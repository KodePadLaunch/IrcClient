package com.kodepad.irc.handler

import com.kodepad.irc.message.Message
import org.slf4j.LoggerFactory

class RplCreatedHandler() : Handler {
    companion object {
        private val logger = LoggerFactory.getLogger(RplCreatedHandler::class.java)
    }

      override suspend fun onMessage(message: Message) {
        logger.debug("message: {}", message)
        
        logger.warn("Handler has no Operations!!")
    }
}