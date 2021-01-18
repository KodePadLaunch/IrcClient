package com.kodepad.irc.codec

import com.kodepad.irc.codec.decoder.Decoder
import com.kodepad.irc.codec.decoder.impl.CharsetDecoderAdapter
import com.kodepad.irc.codec.encoder.Encoder
import com.kodepad.irc.codec.encoder.impl.CharsetEncoderAdapter
import com.kodepad.irc.codec.exception.UnsupportedEncodingException
import java.nio.charset.Charset

object CodecFactoryImpl: CodecFactory {
    private val unsupportedEncodingException = UnsupportedEncodingException("Encoding is not supported yet!")

    override fun getEncoder(encoding: Encoding): Encoder = when(encoding) {
        Encoding.UTF_8 -> CharsetEncoderAdapter(Charset.forName("UTF-8"))
        else -> throw unsupportedEncodingException
    }

    override fun getDecoder(encoding: Encoding): Decoder = when(encoding) {
        Encoding.UTF_8 -> CharsetDecoderAdapter(Charset.forName("UTF-8"))
        else -> throw unsupportedEncodingException
    }
}