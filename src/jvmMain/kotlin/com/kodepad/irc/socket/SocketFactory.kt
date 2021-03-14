package com.kodepad.irc.socket

import com.kodepad.irc.socket.nio.JavaNioSocketImpl
import java.nio.channels.AsynchronousSocketChannel

actual object SocketFactory {
    actual fun create(hostname: String, port: Int, encodedDelimiter: ByteArray): Socket {
        val asynchronousSocketChannel = AsynchronousSocketChannel.open()

        return JavaNioSocketImpl(
            asynchronousSocketChannel,
            hostname,
            port,
            encodedDelimiter
        )
    }
}