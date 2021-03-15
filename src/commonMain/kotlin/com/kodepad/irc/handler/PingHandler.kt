package com.kodepad.irc.handler

import com.kodepad.irc.Message
import com.kodepad.irc.NetworkState
import com.kodepad.irc.command.PongCommand
import com.kodepad.irc.connection.Connection
import com.kodepad.irc.exception.InvalidStateException
import com.kodepad.irc.exception.handler.TargetServerMissingException
import com.kodepad.irc.logging.LoggerFactory

class PingHandler(
    private val connection: Connection,
    private val networkState: NetworkState
) : Handler {
    companion object {
        private val logger = LoggerFactory.getLogger(PingHandler::class)
    }

    override suspend fun onMessage(message: Message) {
        logger.debug("message: {}", message)
        val pongCommand = PongCommand(
            networkState.nickname?: throw InvalidStateException("Nickname is null"),
            message.parameters?.get(0)
                ?: throw TargetServerMissingException("PING target server argument can't be null")
        )

        connection.write(pongCommand.getMessage())
    }
}