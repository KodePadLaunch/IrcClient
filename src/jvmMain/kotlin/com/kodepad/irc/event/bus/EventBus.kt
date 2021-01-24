package com.kodepad.irc.event.bus

interface EventBus<T> {
    fun put(event: T)
}