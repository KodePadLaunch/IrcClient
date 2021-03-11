package com.kodepad.irc.codec

import java.nio.ByteBuffer
import java.nio.CharBuffer
import java.nio.charset.Charset
import java.nio.charset.CharsetDecoder
import java.nio.charset.CharsetEncoder
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class CharsetCodecBridge(private val charset: Charset): Codec {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(CharsetCodecBridge::class.java)
    }

    private val charsetEncoder: CharsetEncoder = charset.newEncoder()
    private val charsetDecoder: CharsetDecoder = charset.newDecoder()

    override fun encode(string: String): ByteArray {
        val byteBuffer = charsetEncoder.encode(CharBuffer.wrap(string))

        return byteBuffer.array().sliceArray(byteBuffer.position() until byteBuffer.limit())
    }

    override fun decode(byteArray: ByteArray): String {
        val charBuffer = charsetDecoder.decode(ByteBuffer.wrap(byteArray))

        return charBuffer.toString()
    }
}