package com.kodepad.irc.socket.factory

import com.kodepad.irc.codec.CodecFactoryImpl
import com.kodepad.irc.codec.Encoding
import com.kodepad.irc.socket.Socket
import com.kodepad.irc.socket.impl.JavaNioAsynchronousSocket
import java.nio.channels.AsynchronousSocketChannel

object JavaNioAsynchronousSocketFactoryImpl: SocketFactory {
    override fun create(hostname: String, port: Int, encodedDelimiter: ByteArray): Socket {
        val asynchronousSocketChannel = AsynchronousSocketChannel.open()

        return JavaNioAsynchronousSocket(
            hostname,
            port,
            asynchronousSocketChannel,
            encodedDelimiter
        )
    }
}