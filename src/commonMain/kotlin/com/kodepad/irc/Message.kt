package com.kodepad.irc

import com.kodepad.irc.event.Event

data class Message(
    val tags: Map<String, String?>?,
    val source: String?,
    val command: String,
    val parameters: List<String>?
): Event
