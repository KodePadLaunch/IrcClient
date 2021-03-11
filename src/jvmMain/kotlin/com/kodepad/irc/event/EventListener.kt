package com.kodepad.irc.event

interface EventListener<T> {
    fun onEvent(event: T)
}
