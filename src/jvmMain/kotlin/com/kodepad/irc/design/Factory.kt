package com.kodepad.irc.design

import kotlin.reflect.KClass

// todo: Just for learning, remove these later
interface Factory<T: Any> {
    fun get(kClass: KClass<out T>): T
}