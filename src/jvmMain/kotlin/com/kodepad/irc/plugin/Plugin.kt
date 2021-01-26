package com.kodepad.irc.plugin

import com.kodepad.irc.dto.Message

interface Plugin {
    fun registeredCommands(): List<String>
    fun onInit()
    fun onServerMessage(message: Message)
}