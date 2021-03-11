package com.kodepad.irc.connection

import com.kodepad.irc.message.Message
import java.io.Closeable

interface Connection: Closeable {
    suspend fun connect()
    suspend fun read(): Message
    suspend fun write(message: Message)
}