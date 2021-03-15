package com.kodepad.irc.connection

import com.kodepad.irc.codec.CodecFactory
import com.kodepad.irc.codec.Encoding
import com.kodepad.irc.exception.IrcClientTestException
import com.kodepad.irc.logging.Markers.TEST_FLOW
import com.kodepad.irc.message.Message
import com.kodepad.irc.serdes.SerDesFactory
import com.kodepad.irc.socket.Socket
import com.kodepad.irc.socket.SocketFactory
import com.kodepad.irc.logging.LoggerFactory
import com.kodepad.kotlinx.coroutines.runBlockingTest
import kotlin.test.Test
import kotlin.test.assertEquals

class ConnectionImplUnitTest {
    companion object {
        val logger = LoggerFactory.getLogger(ConnectionImplUnitTest::class)

        const val delimiter = "\r\n"
    }

    private val hostname = "chat.freenode.net"
    private val port = "6665".toInt()
    private val encoding = Encoding.UTF_8
    private val codec = CodecFactory.getCodec(encoding)
    private val serDes = SerDesFactory.getSerdes(Message::class)

    private val connection = ConnectionImpl(
        SocketFactory.create(
            hostname,
            port,
            codec encode delimiter
        ),
        codec,
        serDes,
    )

    // todo: Write a utility to spin up a dummy server to simulate socket responses
    @Test
    fun `read messages from connection`() {
        val mockSocket = object : Socket {
            override suspend fun open() {
                throw IrcClientTestException("open should not be called!")
            }

            override suspend fun read(): ByteArray {
                val byteArrayList: List<Byte> = listOf(58, 77, 105, 100, 75, 110, 105, 103, 104, 116, 75, 111, 100, 101, 114, 33, 54, 55, 55, 57, 57, 56, 100, 98, 64, 49, 48, 51, 46, 49, 50, 49, 46, 49, 53, 50, 46, 50, 49, 57, 32, 80, 82, 73, 86, 77, 83, 71, 32, 35, 105, 114, 99, 99, 108, 105, 101, 110, 116, 116, 101, 115, 116, 32, 58, 104, 101, 108, 108, 111, 44, 32, 119, 111, 114, 108, 100, 33, 13, 10)

                return byteArrayList.toByteArray()
            }

            override suspend fun write(byteArray: ByteArray): Int {
                throw IrcClientTestException("write should not be called!")
            }

            override suspend fun close() {
                throw IrcClientTestException("close should not be called!")
            }
        }

        val connection = ConnectionImpl(
            mockSocket,
            CodecFactory.getCodec(Encoding.UTF_8),
            SerDesFactory.getSerdes(Message::class)
        )

        val expectedMessage = Message(
            tags=null,
            source="MidKnightKoder!677998db@103.121.152.219",
            command="PRIVMSG",
            parameters=listOf(
                "#ircclienttest",
                "hello, world!"
            )
        )

        runBlockingTest {
            val message = connection.read()
            logger.debug(TEST_FLOW, "message: $message")

            assertEquals(expectedMessage, message)
        }
    }

    @Test
    fun `write messages to connection`() {
        val mockSocket = object : Socket {
            override suspend fun open() {
                throw IrcClientTestException("open should not be called!")
            }

            override suspend fun read(): ByteArray {
                throw IrcClientTestException("read should not be called!")
            }

            override suspend fun write(byteArray: ByteArray): Int {
                val expectedByteArrayList: List<Byte> = listOf(80, 82, 73, 86, 77, 83, 71, 32, 35, 105, 114, 99, 99, 108, 105, 101, 110, 116, 116, 101, 115, 116, 32, 58, 104, 101, 108, 108, 111, 44, 32, 119, 111, 114, 108, 100, 32, 102, 114, 111, 109, 32, 73, 114, 99, 67, 108, 105, 101, 110, 116, 33, 13, 10)

                assertEquals(expectedByteArrayList, byteArray.toList())

                return -1
            }

            override suspend fun close() {
                throw IrcClientTestException("close should not be called!")
            }
        }

        val connection = ConnectionImpl(
            mockSocket,
            CodecFactory.getCodec(Encoding.UTF_8),
            SerDesFactory.getSerdes(Message::class)
        )

        val message = Message(
            tags=null,
            source=null,
            command="PRIVMSG",
            parameters= listOf(
                "#ircclienttest",
                "hello, world from IrcClient!"
            )
        )

        runBlockingTest {
            connection.write(message)
        }
    }
}