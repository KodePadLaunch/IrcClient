package com.kodepad.irc.network

import com.kodepad.irc.event.Event
import com.kodepad.irc.event.EventListener
import com.kodepad.irc.message.Message
import kotlin.reflect.KClass

interface Network {
    fun <T : Event> addEventListener(kClass: KClass<T>, eventListener: EventListener<T>)
    suspend fun connectAndRegister()
    fun getNetworkState(): NetworkState
    suspend fun joinChannel(name: String)
    suspend fun sendMessage(target: String, message: String)
    suspend fun sendRawMessage(message: Message)
    suspend fun close()
}