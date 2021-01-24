package com.kodepad.irc.socket.factory

import com.kodepad.irc.event.bus.impl.FlowBasedEventBus
import com.kodepad.irc.event.subscriber.impl.SocketWriteSuspendingEventSubscriber
import com.kodepad.irc.socket.Socket
import com.kodepad.irc.socket.raw.impl.nio.JavaNioRawSocket
import com.kodepad.irc.socket.impl.SocketImpl
import java.nio.channels.AsynchronousSocketChannel

object JavaNioSocketFactoryImpl: SocketFactory {
    override fun create(hostname: String, port: Int, encodedDelimiter: ByteArray): Socket {
        val asynchronousSocketChannel = AsynchronousSocketChannel.open()
        val javaNioRawSocket = JavaNioRawSocket(
            asynchronousSocketChannel,
            hostname,
            port,
            encodedDelimiter
        )

        val writeEventSubscriber = SocketWriteSuspendingEventSubscriber(javaNioRawSocket)
        val writeEventBus = FlowBasedEventBus(writeEventSubscriber)

        return SocketImpl(
            javaNioRawSocket,
            writeEventBus
        )
    }
}