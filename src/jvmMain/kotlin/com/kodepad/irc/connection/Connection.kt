package com.kodepad.irc.connection

import com.kodepad.irc.dto.Message
import kotlinx.coroutines.flow.Flow
import java.io.Closeable

interface Connection: Closeable {
    fun read(): Flow<Message>
    fun write(message: Message)
}