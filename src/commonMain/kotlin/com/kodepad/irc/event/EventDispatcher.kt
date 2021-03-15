package com.kodepad.irc.event

import kotlin.reflect.KClass

interface EventDispatcher {
    fun<T: Event> addListener(kClass: KClass<T>, eventListener: EventListener<T>)
    fun<T: Event> dispatch(kClass: KClass<T>, event: T)
}