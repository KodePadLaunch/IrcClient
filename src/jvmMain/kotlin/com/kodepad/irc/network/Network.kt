package com.kodepad.irc.network

import java.io.Closeable

interface Network: Closeable {
    fun getNetworkState(): NetworkState
    suspend fun joinChannel(name: String)
    suspend fun sendMessage(target: String, message: String)
}