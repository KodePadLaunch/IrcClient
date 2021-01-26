package com.kodepad.irc.network.impl

import com.kodepad.irc.channel.Channel
import com.kodepad.irc.channel.impl.ChannelImpl
import com.kodepad.irc.connection.Connection
import com.kodepad.irc.dto.Message
import com.kodepad.irc.network.NetworkState
import com.kodepad.irc.network.Network
import com.kodepad.irc.network.NetworkEventListener
import com.kodepad.irc.plugin.Plugin
import com.kodepad.irc.vo.User
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import org.slf4j.LoggerFactory
import kotlin.coroutines.EmptyCoroutineContext

// todo: Test the shutdown logic
open class NetworkImpl(
    private val user: User,
    private val networkEventListener: NetworkEventListener,
    private val networkState: NetworkState,
    private val connection: Connection,
    private val plugins: List<Plugin>
) : Network {
    companion object {
        private val logger = LoggerFactory.getLogger(NetworkImpl::class.java)
    }
//    private val messageFlow: Flow<Message> = connection.read()

    private val coroutineScope = CoroutineScope(EmptyCoroutineContext)

    init {
        plugins.map { plugin ->
            plugin.onInit()
        }

        coroutineScope.launch {
            connection.read().collect { message ->
                // todo: Implement a command based registration for plugins and only invoke if plugin registers for the command.
                plugins.map { plugin ->
                    plugin.onServerMessage(message)
                }
                networkEventListener.onMessage(message)
            }
        }
    }

    override fun getUser(): User = networkState.user

    override fun joinChannel(name: String): Channel {
        val joinMessage = Message(
            null,
            null,
            "JOIN",
            listOf(name)
        )

        connection.write(joinMessage)

        // todo: correct this
        return ChannelImpl(
            name,
            connection
        )
    }

    override fun close() {
        coroutineScope.cancel()
        connection.close()
    }
}