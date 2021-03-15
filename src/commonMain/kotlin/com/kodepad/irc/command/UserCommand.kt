package com.kodepad.irc.command

import com.kodepad.irc.Message

data class UserCommand(
    val username: String,
    val realname: String,
): Command {
    override fun getMessage(): Message = Message(
        null,
        null,
        "USER",
        listOf(username, "0", "*", realname)
    )
}
