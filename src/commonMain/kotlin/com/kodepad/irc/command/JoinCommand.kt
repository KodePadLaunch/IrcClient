package com.kodepad.irc.command

import com.kodepad.irc.Message

data class JoinCommand(
    val channels: List<String>,
    val keys: List<String>? = null
): Command {
    override fun getMessage(): Message {
        val channelsParameters = listOf(channels.joinToString(","))
        val keysParameters = keys?.let { listOf(it.joinToString(",")) }?: emptyList()

        val parameters = channelsParameters + keysParameters

        return Message(
            null,
            null,
            "JOIN",
            parameters
        )
    }
}
