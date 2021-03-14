package com.kodepad.irc.handler

import com.kodepad.irc.message.Message

interface Handler {
    suspend fun onMessage(message: Message)
}
