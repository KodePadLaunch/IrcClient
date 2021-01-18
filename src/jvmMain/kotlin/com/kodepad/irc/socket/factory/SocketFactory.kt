package com.kodepad.irc.socket.factory

import com.kodepad.irc.codec.Encoding
import com.kodepad.irc.socket.Socket

interface SocketFactory {
    fun create(hostname: String, port: Int, encodedDelimiter: ByteArray): Socket
}