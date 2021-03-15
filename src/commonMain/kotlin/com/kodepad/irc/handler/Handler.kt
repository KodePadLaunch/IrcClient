package com.kodepad.irc.handler

import com.kodepad.irc.Message

interface Handler {
    suspend fun onMessage(message: Message)
}
