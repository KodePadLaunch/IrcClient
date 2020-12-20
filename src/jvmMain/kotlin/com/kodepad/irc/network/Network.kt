package com.kodepad.irc.network

import com.kodepad.irc.channel.Channel

interface Network {
    enum class State {
        DISCONNECTED_CLIENT,
        SOCKET_CONNECTED,
        NICK_ACQUIRED,
        DUPLICATE_NICK,
    }

    fun joinChannel(channelName: String, nick: String): Channel
    fun joinChannelWithRegisteredNick(channelName: String, nick: String, password: String): Channel
}