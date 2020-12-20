package com.kodepad.irc.vo

data class Message(
    val tags: Map<String, String?>?,
    val source: String?,
    val command: String,
    val parameters: Map<String, String>?
)