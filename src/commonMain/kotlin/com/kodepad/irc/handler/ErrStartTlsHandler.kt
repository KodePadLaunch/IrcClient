package com.kodepad.irc.handler

import com.kodepad.irc.Message
import com.kodepad.irc.logging.LoggerFactory

class ErrStartTlsHandler() : Handler {
    companion object {
        private val logger = LoggerFactory.getLogger(ErrStartTlsHandler::class)
    }

      override suspend fun onMessage(message: Message) {
        logger.debug("message: {}", message)
        
        logger.warn("Handler has no Operations!!")
    }
}