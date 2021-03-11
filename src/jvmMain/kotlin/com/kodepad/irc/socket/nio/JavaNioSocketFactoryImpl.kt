package com.kodepad.irc.socket.nio

import com.kodepad.irc.socket.Socket
import com.kodepad.irc.socket.SocketFactory
import java.nio.channels.AsynchronousSocketChannel

object JavaNioSocketFactoryImpl: SocketFactory {
    override fun create(hostname: String, port: Int, encodedDelimiter: ByteArray): Socket {
        val asynchronousSocketChannel = AsynchronousSocketChannel.open()

        return JavaNioSocketImpl(
            asynchronousSocketChannel,
            hostname,
            port,
            encodedDelimiter
        )
    }
}