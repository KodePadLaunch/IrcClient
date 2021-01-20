package com.kodepad.irc.network.impl

import com.kodepad.irc.channel.Channel
import com.kodepad.irc.connection.Connection
import com.kodepad.irc.dto.NetworkState
import com.kodepad.irc.network.NetworkInterface

open class NetworkImpl(
    private val hostname: String,
    private val port: Int,
    private val networkState: NetworkState,
    private val connection: Connection
): NetworkInterface {
    override fun joinChannel(channelName: String, nick: String): Channel {
        TODO("Not yet implemented")
    }
    override fun joinChannelWithRegisteredNick(channelName: String, nick: String, password: String): Channel {
        TODO("Not yet implemented")
    }
}