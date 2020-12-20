package com.kodepad.irc.deserializer

interface Deserializer<T> {
    fun deserialize(input: String): T
}