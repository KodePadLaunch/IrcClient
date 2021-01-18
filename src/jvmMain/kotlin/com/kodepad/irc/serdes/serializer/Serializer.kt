package com.kodepad.irc.serdes.serializer

interface Serializer<T> {
    infix fun serialize(input: T): String
}