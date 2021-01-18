package com.kodepad.irc.channel.impl

import com.kodepad.irc.channel.Channel
import com.kodepad.irc.dto.Message
import kotlinx.coroutines.flow.Flow

class ChannelImpl : Channel {
    override fun getNick(): String {
        TODO("Not yet implemented")
    }

    override fun getName(): String {
        TODO("Not yet implemented")
    }

    override fun sendMessage(message: Message): Boolean {
        TODO("Not yet implemented")
    }

    override fun receiveMessage(): Flow<Message> {
        TODO("Not yet implemented")
    }
}