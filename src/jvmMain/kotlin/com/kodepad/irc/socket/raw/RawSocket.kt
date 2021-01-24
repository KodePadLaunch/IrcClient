package com.kodepad.irc.socket.raw

import kotlinx.coroutines.flow.Flow
import java.io.Closeable

interface RawSocket: Closeable {
    fun read(): Flow<ByteArray>
    suspend fun write(byteArray: ByteArray): Int
}
