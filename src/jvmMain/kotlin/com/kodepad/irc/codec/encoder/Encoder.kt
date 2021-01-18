package com.kodepad.irc.codec.encoder

interface Encoder {
    infix fun encode(string: String): ByteArray
}