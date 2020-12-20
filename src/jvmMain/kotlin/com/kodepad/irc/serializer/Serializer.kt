package com.kodepad.irc.serializer

interface Serializer<T> {
    fun serialize(input: T): String
}