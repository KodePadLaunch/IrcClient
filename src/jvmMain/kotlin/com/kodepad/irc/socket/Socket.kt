package com.kodepad.irc.socket

import kotlinx.coroutines.flow.Flow

interface Socket {
    suspend fun read(): Flow<ByteArray>
    suspend fun write(byteArray: ByteArray): Int
}
