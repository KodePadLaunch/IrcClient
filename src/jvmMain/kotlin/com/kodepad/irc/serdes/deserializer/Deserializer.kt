package com.kodepad.irc.serdes.deserializer

interface Deserializer<T> {
    infix fun deserialize(input: String): T
}