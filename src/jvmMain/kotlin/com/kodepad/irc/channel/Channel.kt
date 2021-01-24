package com.kodepad.irc.channel

import com.kodepad.irc.dto.Message
import kotlinx.coroutines.flow.Flow

interface Channel: AutoCloseable {
    fun getName(): String
    fun sendMessage(message: String)
    fun receiveMessage(): Flow<Message>
}