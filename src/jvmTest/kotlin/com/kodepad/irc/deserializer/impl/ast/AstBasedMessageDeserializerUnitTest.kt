package com.kodepad.irc.deserializer.impl.ast

import com.kodepad.irc.codec.CharsetCodecBridge
import com.kodepad.irc.message.Message
import com.kodepad.irc.parser.factory.ParserAbstractFactoryImpl
import com.kodepad.irc.parser.impl.MessageParser
import com.kodepad.irc.serdes.SerDesImpl
import com.kodepad.irc.socket.nio.JavaNioSocketFactoryImpl
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.nio.charset.Charset
import kotlin.test.Test

class AstBasedMessageDeserializerUnitTest {
    companion object {
        private val logger: Logger = LoggerFactory.getLogger(AstBasedMessageDeserializerUnitTest::class.java)

        private const val DELIMTER = "\r\n"
    }

    private val hostname = "chat.freenode.net"
    private val port = Integer.parseInt("6665")
    private val charset = Charset.forName("UTF-8")
    private val codec = CharsetCodecBridge(charset)
    private val parserFactory = ParserAbstractFactoryImpl
    private val serdes = SerDesImpl(parserFactory.get(MessageParser::class))
    private val connection = JavaNioSocketFactoryImpl.create(
        hostname,
        port,
        codec.encode(DELIMTER)
    )

    @Test
    fun `read and write on socket`() {
        logger.debug("started test")

        runBlocking {
            logger.debug("Calling connect")

            launch {
                val message1 = Message(
                    null,
                    null,
                    "CAP",
                    listOf("LS", "302")
                )
//                val message2 = Message(
//                    null,
//                    null,
//                    "PASS",
//                    null
//                )
                val message3 = Message(
                    null,
                    null,
                    "NICK",
                    listOf("MidNightKoder")
                )
                val message4 = Message(
                    null,
                    null,
                    "USER",
                    listOf("MidNightKoder", "0", "*", "Mid Night Koder")
                )
//                val message5 = Message(
//                    null,
//                    null,
//                    "SASL",
//                    null
//                )
                val message6 = Message(
                    null,
                    null,
                    "CAP",
                    listOf("END")
                )
                connection.write(codec encode (serdes serialize  message1))
                logger.info("connection write complete")
//                connection.write(encoder encode (serializer serialize  message2))
                connection.write(codec encode (serdes serialize  message3))
                connection.write(codec encode (serdes serialize  message4))
//                connection.write(encoder encode (serializer serialize  message5))
//                connection.write(encoder encode (serializer serialize  message6))
            }.join()

            logger.debug("Calling read")
//            connection.read().map { byteArray ->
//                logger.debug("got byte array")
//                logger.debug("byteArray: {}", byteArray)
//
//                val string = decoder decode byteArray
//                logger.debug("string: {}", string)
//
//                val message = deserializer deserialize string
//                logger.info("message: {}", message)
//            }.collect()
            logger.debug("read returned")
        }
    }
}