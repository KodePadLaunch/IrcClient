package com.kodepad.irc.codec

import com.kodepad.irc.exception.codec.UnsupportedEncodingException
import java.nio.charset.Charset

actual object CodecFactory {
    actual fun getCodec(encoding: Encoding): Codec = when(encoding) {
        Encoding.UTF_8 -> CharsetCodecBridge(Charset.forName("UTF-8"))
        else -> throw UnsupportedEncodingException("Encoding is not supported yet!")
    }
}