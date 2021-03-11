package com.kodepad.irc.codec

interface Codec {
    infix fun encode(string: String): ByteArray
    infix fun decode(byteArray: ByteArray): String
}