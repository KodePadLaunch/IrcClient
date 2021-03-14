package com.kodepad.irc.logging

import kotlin.reflect.KClass

expect object LoggerFactory {
    inline fun <reified T : Any> getLogger(kClass: KClass<T>): Logger
}