package com.kodepad.irc.connection

import com.kodepad.irc.message.Message

interface Connection {
    suspend fun connect()
    suspend fun read(): Message
    suspend fun write(message: Message)
    suspend fun close()
}