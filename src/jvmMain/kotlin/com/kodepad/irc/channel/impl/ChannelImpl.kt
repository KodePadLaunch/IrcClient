package com.kodepad.irc.channel.impl

import com.kodepad.irc.channel.Channel
import com.kodepad.irc.connection.Connection
import com.kodepad.irc.dto.Message
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.slf4j.LoggerFactory
import kotlin.coroutines.EmptyCoroutineContext

class ChannelImpl(
    private val name: String,
    private val connection: Connection
) : Channel {
    companion object {
        private val logger = LoggerFactory.getLogger(ChannelImpl::class.java)
    }

    private val coroutineScope = CoroutineScope(EmptyCoroutineContext)

    override fun getName(): String {
        return name
    }

    override fun sendMessage(message: String) {
        logger.debug("message: {}", message)
        val privmsg = Message(
            null,
            null,
            "PRIVMSG",
            listOf(name, message)
        )

        connection.write(privmsg)
    }

    override fun receiveMessage(): Flow<Message> {
        TODO("Not yet implemented")
    }

    override fun close() {
        val partMessage = Message(
            null,
            null,
            "PART",
            listOf(name)
        )

        coroutineScope.launch {
            connection.write(partMessage)
        }
    }
}