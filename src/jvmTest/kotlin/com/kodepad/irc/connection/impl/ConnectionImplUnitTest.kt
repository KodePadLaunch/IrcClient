package com.kodepad.irc.connection.impl

import com.kodepad.irc.codec.CodecFactoryImpl
import com.kodepad.irc.codec.Encoding
import com.kodepad.irc.dto.Message
import com.kodepad.irc.serdes.SerdesMessageFactoryImpl
import com.kodepad.irc.socket.factory.JavaNioSocketFactoryImpl
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.test.Test

class ConnectionImplUnitTest {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(ConnectionImplUnitTest::class.java)

        const val delimiter = "\r\n"
    }

    private val hostname = "chat.freenode.net"
    private val port = Integer.parseInt("6665")
    private val encoding = Encoding.UTF_8
    private val encoder = CodecFactoryImpl.getEncoder(encoding)
    private val decoder = CodecFactoryImpl.getDecoder(encoding)
    private val serializer = SerdesMessageFactoryImpl.getSerializer()
    private val deserializer = SerdesMessageFactoryImpl.getDeserializer()

    private val connection = ConnectionImpl(
        JavaNioSocketFactoryImpl.create(
            hostname,
            port,
            encoder encode delimiter
        ),
        encoder,
        decoder,
        serializer,
        deserializer
    )


    @Test
    fun `register and send message to channel`() {
        val nick = "dummykodepadnick"
        val user = "ircclienttestuser"
        val fullName = "IRC Client Test Host"
        val channelName = "#ircclienttest"

        val message1 = Message(
            null,
            null,
            "CAP",
            listOf("LS", "302")
        )
        val message2 = Message(
            null,
            null,
            "PASS",
            null
        )
        val message3 = Message(
            null,
            null,
            "NICK",
            listOf(nick)
        )
        val message4 = Message(
            null,
            null,
            "USER",
            listOf(user, "0", "*", fullName)
        )
        val message5 = Message(
            null,
            null,
            "SASL",
            null
        )
        val message6 = Message(
            null,
            null,
            "CAP",
            listOf("END")
        )
        val message7 = Message(
            null,
            null,
            "JOIN",
            listOf("#ircclienttest")
        )
        val message8 = Message(
            null,
            null,
            "CAP",
            listOf("END")
        )
        val message9 = Message(
            null,
            null,
            "CAP",
            listOf("END")
        )

        logger.info("starting test")
        runBlocking {
            launch {
                connection.write(message3)
                connection.write(message4)
                connection.write(message7)
            }
            logger.info("Registration commands sent")
            launch {
                connection.read().map {
                    logger.info("message: {}", it)
                }.collect()
            }
        }
    }
}