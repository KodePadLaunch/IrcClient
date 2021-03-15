package com.kodepad.irc.command

import com.kodepad.irc.Message

data class NickCommand(
    val nickname: String
): Command {
    override fun getMessage(): Message = Message(
        null,
        null,
        "NICK",
        listOf(
            nickname
        )
    )
}
