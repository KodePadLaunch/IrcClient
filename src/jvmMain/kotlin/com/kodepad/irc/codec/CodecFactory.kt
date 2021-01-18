package com.kodepad.irc.codec

import com.kodepad.irc.codec.decoder.Decoder
import com.kodepad.irc.codec.encoder.Encoder

interface CodecFactory {
    fun getEncoder(encoding: Encoding): Encoder
    fun getDecoder(encoding: Encoding): Decoder
}