package com.kodepad.irc.handler

import com.kodepad.irc.event.EventListener
import com.kodepad.irc.exception.handler.UnknownCommandException
import com.kodepad.irc.message.Message
import org.slf4j.LoggerFactory

class MessageHandler(
    private val commandHandlerFactory: CommandHandlerFactory,
    private val rawMessageEventListener: EventListener<Message>? = null,
) : Handler {
    companion object {
        private val logger = LoggerFactory.getLogger(MessageHandler::class.java)
    }

    override suspend fun onMessage(message: Message) {
        logger.debug("message: {}", message)

        rawMessageEventListener?.onEvent(message)

        try {
            val commandHandler = commandHandlerFactory.getHandler(Command.valueOf("COMMAND_" + message.command))
            commandHandler.onMessage(message)
        } catch (exception: IllegalArgumentException) {
            logger.debug("Handler not found for ${message.command}")
            logger.error("Handler not found", UnknownCommandException(exception))
        }
    }
}