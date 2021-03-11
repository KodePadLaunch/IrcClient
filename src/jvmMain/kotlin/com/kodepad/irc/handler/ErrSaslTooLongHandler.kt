package com.kodepad.irc.handler

import com.kodepad.irc.message.Message
import org.slf4j.LoggerFactory

class ErrSaslTooLongHandler() : Handler {
    companion object {
        private val logger = LoggerFactory.getLogger(ErrSaslTooLongHandler::class.java)
    }

      override suspend fun onMessage(message: Message) {
        logger.debug("message: {}", message)
        
        logger.warn("Handler has no Operations!!")
    }
}