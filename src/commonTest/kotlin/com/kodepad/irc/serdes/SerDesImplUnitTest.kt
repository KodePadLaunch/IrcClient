package com.kodepad.irc.serdes

import com.kodepad.irc.logging.Markers.TEST_FLOW
import com.kodepad.irc.message.Message
import kotlin.test.Test
import kotlin.test.assertEquals
import com.kodepad.irc.logging.Logger
import com.kodepad.irc.logging.LoggerFactory

class SerDesImplUnitTest {
    companion object {
        private val logger: Logger = LoggerFactory.getLogger(SerDesImplUnitTest::class)
    }

    // todo: Separate performance test from functionality test and avoid logs in performance test
    @Test(/* timeout = DESERIALIZATION_TIMEOUT_IN_MILIS */)
    fun `message deserialization test`() {
        val input = ":tepper.freenode.net 005 dummykodepadnick CPRIVMSG CNOTICE SAFELIST ELIST=CTU MONITOR=100 :are supported by this server\r\n"
        val expectedMessage = Message(
            tags=null,
            source="tepper.freenode.net",
            command="005",
            parameters= listOf(
                "dummykodepadnick",
                "CPRIVMSG",
                "CNOTICE",
                "SAFELIST",
                "ELIST=CTU",
                "MONITOR=100",
                "are supported by this server",
            )
        )

        val serDes = SerDesFactory.getSerdes(Message::class)

        val message = serDes.deserialize(input)
        logger.debug(TEST_FLOW, "input: $input")
        logger.debug(TEST_FLOW, "expected: $expectedMessage")
        logger.debug(TEST_FLOW, "message : $message")

        assertEquals(expectedMessage, message)
    }

    @Test(/* timeout = SERIALIZATION_TIMEOUT_IN_MILIS */)
    fun `message serialization test`() {
        val expectedMessageLine = ":tepper.freenode.net 005 dummykodepadnick CPRIVMSG CNOTICE SAFELIST ELIST=CTU MONITOR=100 :are supported by this server\r\n"
        val message = Message(
            tags=null,
            source="tepper.freenode.net",
            command="005",
            parameters= listOf(
                "dummykodepadnick",
                "CPRIVMSG",
                "CNOTICE",
                "SAFELIST",
                "ELIST=CTU",
                "MONITOR=100",
                "are supported by this server",
            )
        )

        val serDes = SerDesFactory.getSerdes(Message::class)

        val messageLine = serDes.serialize(message)
        logger.debug(TEST_FLOW, "message: $message")
        logger.debug(TEST_FLOW, "messageLine: $messageLine")

        assertEquals(expectedMessageLine, messageLine)
    }

    @Test()
    fun `serialization and deserialization inverse relation test`() {
        val input = ":tepper.freenode.net 005 dummykodepadnick CPRIVMSG CNOTICE SAFELIST ELIST=CTU MONITOR=100 :are supported by this server\r\n"

        val serDes = SerDesFactory.getSerdes(Message::class)

        val output = serDes.serialize(serDes.deserialize(input))
        logger.debug(TEST_FLOW, "input : $input")
        logger.debug(TEST_FLOW, "output: $output")

        assertEquals(input, output)
    }
}