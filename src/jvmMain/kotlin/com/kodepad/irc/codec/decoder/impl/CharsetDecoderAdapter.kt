package com.kodepad.irc.codec.decoder.impl

import com.kodepad.irc.codec.decoder.Decoder
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.nio.ByteBuffer
import java.nio.charset.Charset
import java.nio.charset.CharsetDecoder

class CharsetDecoderAdapter(private val charset: Charset): Decoder {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(CharsetDecoderAdapter::class.java)
    }

    private val charsetDecoder: CharsetDecoder = charset.newDecoder()

    override fun decode(byteArray: ByteArray): String {
        val charBuffer = charsetDecoder.decode(ByteBuffer.wrap(byteArray)).toString()

        return charBuffer.toString()
    }
}