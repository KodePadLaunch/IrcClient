package com.kodepad.irc.connection

import com.kodepad.irc.dto.Message
import kotlinx.coroutines.flow.Flow

interface Connection {
    suspend fun read(): Flow<Message>
    suspend fun write(message: Message): Int
}