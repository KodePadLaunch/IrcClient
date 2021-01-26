package com.kodepad.irc

import com.kodepad.irc.codec.Encoding
import com.kodepad.irc.network.Network
import com.kodepad.irc.network.NetworkEventListener
import com.kodepad.irc.plugin.PluginFactory
import com.kodepad.irc.vo.User

interface Client {
    fun joinNetwork(
        hostname: String,
        port: Int,
        user: User,
        networkEventListener: NetworkEventListener,
        encoding: Encoding = Encoding.UTF_8,
        pluginFactories: List<PluginFactory> = listOf()
    ): Network
}