package com.kodepad.irc.network.impl

import com.kodepad.irc.channel.Channel
import com.kodepad.irc.channel.ChannelEventListener
import com.kodepad.irc.channel.impl.ChannelImpl
import com.kodepad.irc.connection.Connection
import com.kodepad.irc.dto.Message
import com.kodepad.irc.dto.NetworkState
import com.kodepad.irc.event.bus.EventBus
import com.kodepad.irc.network.Network
import com.kodepad.irc.network.NetworkEventListener
import com.kodepad.irc.vo.User
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import org.slf4j.LoggerFactory
import kotlin.coroutines.EmptyCoroutineContext

open class NetworkImpl(
    private val user: User,
    private val networkEventListener: NetworkEventListener,
    private val networkState: NetworkState,
    private val connection: Connection
) : Network {
    companion object {
        private val logger = LoggerFactory.getLogger(NetworkImpl::class.java)
    }
//    private val messageFlow: Flow<Message> = connection.read()

    private val coroutineScope = CoroutineScope(EmptyCoroutineContext)

    init {
        coroutineScope.launch {
            connection.read().collect { message ->
                networkEventListener.onMessage(message)
            }
        }

        // todo: Formalize this
        val nickMessage = Message(
            null,
            null,
            "NICK",
            listOf(user.nickname)
        )
        val userMessage = Message(
            null,
            null,
            "USER",
            listOf(user.username, "0", "*", user.realname)
        )

        logger.debug("nickMessage: {}", nickMessage)
        logger.debug("userMessage: {}", userMessage)

        connection.write(nickMessage)
        connection.write(userMessage)
    }

    override fun getNick(): String {
        TODO("Not yet implemented")
    }

    override fun joinChannel(name: String, channelEventListener: ChannelEventListener): Channel {
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

//    override fun joinChannelWithRegisteredNick(channelName: String, nick: String, password: String): Channel {
//        TODO("Not yet implemented")
//    }

    override fun close() {
        coroutineScope.cancel()
        connection.close()
    }
}