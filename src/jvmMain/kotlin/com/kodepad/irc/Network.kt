package com.kodepad.irc

import com.kodepad.irc.codec.CodecFactoryImpl
import com.kodepad.irc.codec.Encoding
import com.kodepad.irc.connection.impl.ConnectionImpl
import com.kodepad.irc.dto.NetworkState
import com.kodepad.irc.network.impl.NetworkImpl
import com.kodepad.irc.serdes.SerdesMessageFactoryImpl
import com.kodepad.irc.socket.factory.JavaNioAsynchronousSocketFactoryImpl

class Network(
    hostname: String,
    port: Int,
    encoding: Encoding = Encoding.UTF_8
): NetworkImpl(
    hostname,
    port,
    DEFAULT_NETWORK_STATE,
    ConnectionImpl(
        JavaNioAsynchronousSocketFactoryImpl.create(
            hostname,
            port,
            CodecFactoryImpl.getEncoder(encoding).encode(DELIMITER)
        ),
        CodecFactoryImpl.getEncoder(encoding),
        CodecFactoryImpl.getDecoder(encoding),
        SerdesMessageFactoryImpl.getSerializer(),
        SerdesMessageFactoryImpl.getDeserializer()
    )
) {
    companion object {
        const val DELIMITER = "\r\n"
        val DEFAULT_NETWORK_STATE = NetworkState(
            "dummy state"
        )
    }
}