package com.kodepad.irc.handler

import com.kodepad.irc.logging.LoggerFactory
import com.kodepad.irc.Message

class RplUModeIsHandler() : Handler {
    companion object {
        private val logger = LoggerFactory.getLogger(RplUModeIsHandler::class)
    }

      override suspend fun onMessage(message: Message) {
        logger.debug("message: {}", message)
        
        logger.warn("Handler has no Operations!!")
    }
}