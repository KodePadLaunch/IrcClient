package com.kodepad.irc.channel

import com.kodepad.irc.vo.Message
import kotlinx.coroutines.flow.*

interface Channel {
    fun getNick(): String
    fun getName(): String
    fun sendMessage(message: Message): Boolean
    fun receiveMessage(): Flow<Message>
}