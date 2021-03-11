package com.kodepad.irc.socket

interface SocketFactory {
    fun create(hostname: String, port: Int, encodedDelimiter: ByteArray): Socket
}