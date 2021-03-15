package com.kodepad.irc.command

import com.kodepad.irc.Message

data class PongCommand(
    val source: String,
    val target: String,
): Command {
    override fun getMessage(): Message = Message(
        null,
        null,
        "PONG",
        listOf(
            source,
            target
        )
    )
}
