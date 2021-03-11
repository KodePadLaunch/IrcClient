package com.kodepad.irc.serdes

interface SerDes<T> {
    infix fun serialize(input: T): String
    infix fun deserialize(input: String): T
}