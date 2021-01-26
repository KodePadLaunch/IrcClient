package com.kodepad.irc.plugin

import com.kodepad.irc.dto.Message
import com.kodepad.irc.network.NetworkState

interface PluginHook {
    val networkState: NetworkState

    fun sendMessageToServer(message: Message)
    fun sendMessageToUser(message: Message)
}
