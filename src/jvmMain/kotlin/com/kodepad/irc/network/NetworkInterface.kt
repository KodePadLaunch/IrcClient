package com.kodepad.irc.network

import com.kodepad.irc.channel.Channel

interface NetworkInterface {
    fun joinChannel(channelName: String, nick: String): Channel
    fun joinChannelWithRegisteredNick(channelName: String, nick: String, password: String): Channel
}