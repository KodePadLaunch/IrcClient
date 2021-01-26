package com.kodepad.irc

import com.kodepad.irc.codec.CodecFactoryImpl
import com.kodepad.irc.codec.Encoding
import com.kodepad.irc.connection.impl.ConnectionImpl
import com.kodepad.irc.network.NetworkState
import com.kodepad.irc.network.Network
import com.kodepad.irc.network.NetworkEventListener
import com.kodepad.irc.network.impl.NetworkImpl
import com.kodepad.irc.plugin.PluginFactory
import com.kodepad.irc.plugin.impl.PluginHookImpl
import com.kodepad.irc.plugin.impl.ping.PingPluginFactory
import com.kodepad.irc.plugin.impl.registration.RegistrationPluginFactory
import com.kodepad.irc.serdes.SerdesMessageFactoryImpl
import com.kodepad.irc.socket.factory.JavaNioSocketFactoryImpl
import com.kodepad.irc.vo.User
import org.slf4j.LoggerFactory

class IrcClient: Client {
    companion object {
        private val logger = LoggerFactory.getLogger(IrcClient::class.java)

        const val DELIMITER = "\r\n"
    }

    override fun joinNetwork(
        hostname: String,
        port: Int,
        user: User,
        networkEventListener: NetworkEventListener,
        encoding: Encoding,
        customPluginFactories: List<PluginFactory>
    ): Network {
        val networkState = NetworkState(
            user
        )

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

        val pluginHook = PluginHookImpl(
            connection,
            networkEventListener,
            networkState
        )

        val defaultPluginFactories = listOf(
            RegistrationPluginFactory,
            PingPluginFactory
        )

        val pluginFactories = defaultPluginFactories + customPluginFactories

        val plugins = pluginFactories.map { pluginFactory ->
            pluginFactory.create(pluginHook)
        }

        return NetworkImpl(
            user,
            networkEventListener,
            networkState,
            connection,
            plugins
        )
    }
}