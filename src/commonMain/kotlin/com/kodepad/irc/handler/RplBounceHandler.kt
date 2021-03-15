package com.kodepad.irc.handler

import com.kodepad.irc.logging.LoggerFactory
import com.kodepad.irc.message.Message

class RplBounceHandler() : Handler {
    companion object {
        private val logger = LoggerFactory.getLogger(RplBounceHandler::class)
    }

      override suspend fun onMessage(message: Message) {
        logger.debug("message: {}", message)
        
        logger.warn("Handler has no Operations!!")
    }
}