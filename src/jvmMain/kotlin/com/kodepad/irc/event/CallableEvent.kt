package com.kodepad.irc.event

interface CallableEvent<T, R> {
    val eventData: T
    fun onSuccess(value: R)
    fun onException(exception: Exception)
}