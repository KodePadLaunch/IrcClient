package com.kodepad.irc.network

interface Network {
    suspend fun connectAndRegister()
    fun getNetworkState(): NetworkState
    suspend fun joinChannel(name: String)
    suspend fun sendMessage(target: String, message: String)
    suspend fun close()
}