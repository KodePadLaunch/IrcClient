package com.kodepad.irc.dto

data class Message(
    val tags: Map<String, String?>?,
    val source: String?,
    val command: String,
    val parameters: List<String>?
)