package com.kodepad.irc.logging

import kotlin.reflect.KClass
import org.slf4j.LoggerFactory

actual object LoggerFactory {
    actual inline fun <reified T: Any> getLogger(kClass: KClass<T>): Logger {
        return Slf4jToIrcLoggerAdapter(LoggerFactory.getLogger(T::class.java))
    }
}