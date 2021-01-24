package com.kodepad.irc.event.subscriber

interface SuspendingEventSubscriber<T> {
    suspend fun onEvent(event: T)
}