package com.kodepad.irc.connection

import com.kodepad.irc.IrcNetwork
import com.kodepad.irc.Message
import com.kodepad.irc.codec.CodecFactory
import com.kodepad.irc.codec.Encoding
import com.kodepad.irc.event.EventDispatcher
import com.kodepad.irc.serdes.SerDesFactory
import com.kodepad.irc.socket.SocketFactory
import com.kodepad.irc.state.MutableNetworkState

object ConnectionFactory {
    fun create(
        hostname: String,
        port: Int,
        encoding: Encoding,
        eventDispatcher: EventDispatcher,
        mutableNetworkState: MutableNetworkState,
    ): Connection {
        val connectionImpl = ConnectionImpl(
            SocketFactory.create(
                hostname,
                port,
                CodecFactory.getCodec(encoding).encode(IrcNetwork.DELIMITER)
            ),
            CodecFactory.getCodec(encoding),
            SerDesFactory.getSerdes(Message::class),
        )

        return ConnectionExceptionCaptureDecorator(
            connectionImpl, eventDispatcher, mutableNetworkState
        )
    }
}