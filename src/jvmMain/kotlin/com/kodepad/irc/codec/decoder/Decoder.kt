package com.kodepad.irc.codec.decoder

interface Decoder {
    infix fun decode(byteArray: ByteArray): String
}