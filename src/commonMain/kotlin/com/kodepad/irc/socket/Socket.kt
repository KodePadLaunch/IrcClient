package com.kodepad.irc.socket

interface Socket {
    suspend fun open()
    suspend fun read(): ByteArray
    suspend fun write(byteArray: ByteArray): Int
    suspend fun close()
}
