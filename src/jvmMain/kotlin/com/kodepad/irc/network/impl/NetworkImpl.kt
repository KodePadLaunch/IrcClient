package com.kodepad.irc.network.impl

import com.kodepad.irc.channel.Channel
import com.kodepad.irc.connection.Connection
import com.kodepad.irc.network.Network

class NetworkImpl(hostname: String, port: Int): Network {
    private val connection = Connection(hostname, port)

    override fun joinChannel(channelName: String, nick: String): Channel {
        TODO("Not yet implemented")
    }
    override fun joinChannelWithRegisteredNick(channelName: String, nick: String, password: String): Channel {
        TODO("Not yet implemented")
    }
}