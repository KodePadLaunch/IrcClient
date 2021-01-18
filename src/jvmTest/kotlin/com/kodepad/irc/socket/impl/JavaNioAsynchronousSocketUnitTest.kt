package com.kodepad.irc.socket.impl

import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.nio.ByteBuffer
import java.nio.CharBuffer
import java.nio.channels.AsynchronousSocketChannel
import java.nio.charset.Charset
import kotlin.test.Test

class JavaNioAsynchronousSocketUnitTest {
    companion object {
        private val logger: Logger = LoggerFactory.getLogger(JavaNioAsynchronousSocketUnitTest::class.java)

        private const val delimiter = "\r\n"
    }

    private val hostname = "chat.freenode.net"
    private val port = Integer.parseInt("6665")
    private val charsetUTF8 = Charset.forName("UTF-8")
    private val charsetUTF8Decoder = charsetUTF8.newDecoder()
    private val charsetUTF8Encoder = charsetUTF8.newEncoder()
    private val delimterByteArray = charsetUTF8Encoder.encode(CharBuffer.wrap(delimiter)).array()
    private val connection = JavaNioAsynchronousSocket(
        hostname,
        port,
        AsynchronousSocketChannel.open(),
        delimterByteArray
    )

    @Test
    fun `read from socket`() {
        logger.debug("started test")

        runBlocking {
            logger.debug("Calling connect")
            logger.debug("Calling read")
            connection.read().map { byteArray ->
                logger.debug("got byte array")
                val outputCharBuffer = charsetUTF8Decoder.decode(ByteBuffer.wrap(byteArray))

                val position = outputCharBuffer.position()
                val limit = outputCharBuffer.limit()
                logger.debug("position: {}", position)
                logger.debug("limit: {}", limit)
                val outputString = outputCharBuffer.toString()
                logger.debug("outputString: {}", outputString)

                outputCharBuffer.clear()
            }.collect()
            logger.debug("read returned")
        }
    }
}