package com.kodepad.irc.handler

import com.kodepad.irc.event.MessageEventListener
import com.kodepad.irc.exception.handler.UnknownCommandException
import com.kodepad.irc.message.Message
import org.slf4j.LoggerFactory

class MessageHandler(
        private val onMessageEventListener: MessageEventListener?,
        private val commandHandlerFactory: CommandHandlerFactory
) : Handler {
    companion object {
        private val logger = LoggerFactory.getLogger(MessageHandler::class.java)
    }

    override suspend fun onMessage(message: Message) {
        logger.debug("message: {}", message)

        onMessageEventListener?.onEvent(message)

        try {
            val commandHandler = commandHandlerFactory.getHandler(Command.valueOf("COMMAND_" + message.command))
            commandHandler.onMessage(message)
        } catch (exception: IllegalArgumentException) {
            logger.debug("Handler not found for ${message.command}")
            logger.error("Handler not found", UnknownCommandException(exception))
        }
    }
}