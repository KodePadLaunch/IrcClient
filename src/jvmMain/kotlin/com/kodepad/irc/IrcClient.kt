package com.kodepad.irc

import com.kodepad.irc.codec.CodecFactoryImpl
import com.kodepad.irc.codec.Encoding
import com.kodepad.irc.connection.impl.ConnectionImpl
import com.kodepad.irc.dto.NetworkState
import com.kodepad.irc.network.Network
import com.kodepad.irc.network.NetworkEventListener
import com.kodepad.irc.network.impl.NetworkImpl
import com.kodepad.irc.serdes.SerdesMessageFactoryImpl
import com.kodepad.irc.socket.factory.JavaNioSocketFactoryImpl
import com.kodepad.irc.vo.User
import org.slf4j.LoggerFactory

class IrcClient: Client {
    companion object {
        private val logger = LoggerFactory.getLogger(IrcClient::class.java)

        const val DELIMITER = "\r\n"
        val DEFAULT_NETWORK_STATE = NetworkState(
            "dummy state"
        )
    }

    override fun joinNetwork(
        hostname: String,
        port: Int,
        user: User,
        networkEventListener: NetworkEventListener,
        encoding: Encoding
    ): Network {
        val connection = ConnectionImpl(
            JavaNioSocketFactoryImpl.create(
                hostname,
                port,
                CodecFactoryImpl.getEncoder(encoding).encode(DELIMITER)
            ),
            CodecFactoryImpl.getEncoder(encoding),
            CodecFactoryImpl.getDecoder(encoding),
            SerdesMessageFactoryImpl.getSerializer(),
            SerdesMessageFactoryImpl.getDeserializer()
        )

        return NetworkImpl(
            user,
            networkEventListener,
            DEFAULT_NETWORK_STATE,
            connection
        )
    }
}