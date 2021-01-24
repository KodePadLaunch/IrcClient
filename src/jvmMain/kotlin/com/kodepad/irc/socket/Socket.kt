package com.kodepad.irc.socket

import kotlinx.coroutines.flow.Flow
import java.io.Closeable

interface Socket: Closeable {
    fun read(): Flow<ByteArray>
    fun write(byteArray: ByteArray)
}
