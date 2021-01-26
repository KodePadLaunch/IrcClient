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
// todo: This class is doing too much break it down
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
    private val pluginsForCommand: Map<String, List<Plugin>>
    init {
        val pluginsForCommandMutable = mutableMapOf<String, ArrayList<Plugin>>()

        for(plugin in plugins) {
            plugin.onInit()

            for(command in plugin.registeredCommands()) {
                pluginsForCommandMutable.getOrPut(command, { arrayListOf() }).add(plugin)
            }
        }

        pluginsForCommand = HashMap(pluginsForCommandMutable)
    }

    private val coroutineScope = CoroutineScope(EmptyCoroutineContext)

    init {
        coroutineScope.launch {
            connection.read().collect { message ->
                val plugins = pluginsForCommand[message.command]

                plugins?.map { plugin ->
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