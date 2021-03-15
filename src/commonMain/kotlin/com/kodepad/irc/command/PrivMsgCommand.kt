package com.kodepad.irc.command

import com.kodepad.irc.Message

data class PrivMsgCommand(
    val targets: List<String>,
    val text: String
): Command {
    override fun getMessage(): Message = Message(
        null,
        null,
        "PRIVMSG",
        listOf(targets.joinToString(","), text)
    )
}
