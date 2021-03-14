package com.kodepad.irc.logging

interface Logger {
    fun trace(msg: String)
    fun trace(format: String, arg: Any)

    fun debug(msg: String)
    fun debug(format: String, arg: Any)
    fun debug(format: String, arg1: Any, arg2: Any)
    fun debug(format: String, vararg arguments: Any)
    fun debug(marker: Marker, msg: String)
    fun debug(marker: Marker, format: String, arg: Any)

    fun info(msg: String)
    fun info(format: String, arg: Any)

    fun warn(msg: String)
    fun warn(format: String, arg: Any)

    fun error(msg: String)
    fun error(format: String, arg: Any)
}