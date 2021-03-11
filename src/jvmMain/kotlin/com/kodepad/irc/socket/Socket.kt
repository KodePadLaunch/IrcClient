package com.kodepad.irc.socket

import java.io.Closeable

interface Socket: Closeable {
    suspend fun open()
    suspend fun read(): ByteArray
    suspend fun write(byteArray: ByteArray): Int
}
