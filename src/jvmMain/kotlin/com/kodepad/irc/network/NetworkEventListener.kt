package com.kodepad.irc.network

import com.kodepad.irc.dto.Message

interface NetworkEventListener {
    fun onMessage(message: Message)
}