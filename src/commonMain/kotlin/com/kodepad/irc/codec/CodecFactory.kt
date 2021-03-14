package com.kodepad.irc.codec

expect object CodecFactory {
    fun getCodec(encoding: Encoding): Codec
}