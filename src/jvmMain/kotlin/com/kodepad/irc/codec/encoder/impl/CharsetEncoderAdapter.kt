package com.kodepad.irc.codec.encoder.impl

import com.kodepad.irc.codec.encoder.Encoder
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.nio.CharBuffer
import java.nio.charset.Charset
import java.nio.charset.CharsetEncoder

class CharsetEncoderAdapter(private val charset: Charset): Encoder {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(CharsetEncoderAdapter::class.java)
    }

    private val charsetEncoder: CharsetEncoder = charset.newEncoder()

    override fun encode(string: String): ByteArray {
        val byteBuffer = charsetEncoder.encode(CharBuffer.wrap(string))

        return byteBuffer.array().sliceArray(byteBuffer.position() until byteBuffer.limit())
    }
}