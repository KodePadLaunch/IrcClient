package com.kodepad.irc.connection.impl

import com.kodepad.irc.codec.CodecFactory
import com.kodepad.irc.connection.ConnectionImpl
import com.kodepad.irc.codec.Encoding
import com.kodepad.irc.message.Message
import com.kodepad.irc.serdes.SerDesFactory
import com.kodepad.irc.socket.nio.JavaNioSocketFactoryImpl
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
    private val codec = CodecFactory.getCodec(encoding)
    private val serDes = SerDesFactory.getSerdes(Message::class)

    private val connection = ConnectionImpl(
        JavaNioSocketFactoryImpl.create(
            hostname,
            port,
            codec encode delimiter
        ),
        codec,
        serDes,
    )


    @Test
    fun `read messages from connection`() {

    }
}