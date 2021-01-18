package com.kodepad.irc.serdes

import com.kodepad.irc.serdes.deserializer.Deserializer
import com.kodepad.irc.serdes.serializer.Serializer

interface SerdesFactory<T> {
    fun getSerializer(): Serializer<T>
    fun getDeserializer(): Deserializer<T>
}