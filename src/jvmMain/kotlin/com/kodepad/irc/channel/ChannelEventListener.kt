package com.kodepad.irc.channel

import com.kodepad.irc.dto.Message

interface ChannelEventListener {
    fun onMessage(message: Message)
}