package com.kodepad.irc.network

import com.kodepad.irc.event.Event
import com.kodepad.irc.event.EventListener
import com.kodepad.irc.Message
import com.kodepad.irc.NetworkState
import com.kodepad.irc.command.JoinCommand
import com.kodepad.irc.command.NickCommand
import com.kodepad.irc.command.PrivMsgCommand
import com.kodepad.irc.command.UserCommand
import kotlin.reflect.KClass

interface Network {
    fun <T : Event> addEventListener(kClass: KClass<T>, eventListener: EventListener<T>)
    suspend fun connectAndRegister(nickCommand: NickCommand, userCommand: UserCommand)
    fun getNetworkState(): NetworkState
    suspend fun joinChannel(joinCommand: JoinCommand)
    suspend fun sendPrivMsg(privMsgCommand: PrivMsgCommand)
    suspend fun sendRawMessage(message: Message)
    suspend fun close()
}