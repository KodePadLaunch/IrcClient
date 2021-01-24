package com.kodepad.irc.network

import com.kodepad.irc.channel.Channel
import com.kodepad.irc.dto.Message
import com.kodepad.irc.channel.ChannelEventListener
import kotlinx.coroutines.flow.Flow
import java.io.Closeable

interface Network: Closeable {
    fun getNick(): String
    fun joinChannel(name: String, channelEventListener: ChannelEventListener): Channel
//    fun joinChannelWithRegisteredNick(channelName: String, nick: String, password: String): Channel
}