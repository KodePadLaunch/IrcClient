package com.kodepad.irc.message

data class Message(
    val tags: Map<String, String?>?,
    val source: String?,
    val command: String,
    val parameters: List<String>?
)
