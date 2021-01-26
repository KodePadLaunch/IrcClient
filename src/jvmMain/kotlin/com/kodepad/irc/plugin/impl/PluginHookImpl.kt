package com.kodepad.irc.plugin.impl

import com.kodepad.irc.connection.Connection
import com.kodepad.irc.dto.Message
import com.kodepad.irc.network.NetworkEventListener
import com.kodepad.irc.network.NetworkState
import com.kodepad.irc.plugin.PluginHook

class PluginHookImpl(
    private val connection: Connection,
    private val networkEventListener: NetworkEventListener,
    override val networkState: NetworkState
    ): PluginHook {
    override fun sendMessageToServer(message: Message) {
        connection.write(message)
    }

    override fun sendMessageToUser(message: Message) {
        networkEventListener.onMessage(message)
    }
}