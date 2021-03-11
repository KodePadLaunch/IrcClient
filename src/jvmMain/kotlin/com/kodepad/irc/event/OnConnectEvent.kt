package com.kodepad.irc.event

import com.kodepad.irc.message.Message

data class OnConnectEvent(
        val message: Message
)
