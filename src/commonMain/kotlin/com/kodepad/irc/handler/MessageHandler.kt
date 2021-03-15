package com.kodepad.irc.handler

import com.kodepad.irc.Message
import com.kodepad.irc.event.EventDispatcher
import com.kodepad.irc.exception.handler.UnknownCommandException
import com.kodepad.irc.logging.LoggerFactory

class MessageHandler(
    private val commandHandlerFactory: CommandHandlerFactory,
    private val eventDispatcher: EventDispatcher,
) : Handler {
    companion object {
        private val logger = LoggerFactory.getLogger(MessageHandler::class)
    }

    override suspend fun onMessage(message: Message) {
        logger.debug("message: {}", message)

        eventDispatcher.dispatch(Message::class, message)

        try {
            val commandHandler = commandHandlerFactory.getHandler(Command.valueOf("COMMAND_" + message.command))
            commandHandler.onMessage(message)
        } catch (exception: IllegalArgumentException) {
            logger.debug("Handler not found for ${message.command}")
            logger.error("Handler not found", UnknownCommandException(exception))
        }
    }
}