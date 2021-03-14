package com.kodepad.irc.socket

expect object SocketFactory {
    fun create(hostname: String, port: Int, encodedDelimiter: ByteArray): Socket
}