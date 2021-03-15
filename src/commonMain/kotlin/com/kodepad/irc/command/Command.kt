package com.kodepad.irc.command

import com.kodepad.irc.Message

interface Command {
    fun getMessage(): Message
}
