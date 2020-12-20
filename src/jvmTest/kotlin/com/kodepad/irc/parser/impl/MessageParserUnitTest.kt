package com.kodepad.irc.parser.impl

import com.kodepad.irc.parser.factory.ParserFactory
import com.kodepad.irc.parser.factory.ParserAbstractFactoryImpl
import com.kodepad.irc.parser.impl.MessageParser
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.test.Test
import kotlin.test.assertEquals

class MessageParserUnitTest {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(MessageParserUnitTest::class.java)
    }

    private val parserFactory: ParserFactory = ParserAbstractFactoryImpl
    private val parser = parserFactory.get(MessageParser::class) as MessageParser

    @Test
    fun `parsing example 1`() {
        val input = ":irc.example.com CAP LS * :multi-prefix extended-join sasl\r\n"

        val ast = parser.parse(input)

        val expectedMatchedString = ":irc.example.com CAP LS * :multi-prefix extended-join sasl\r\n"
        assertEquals(expectedMatchedString, ast.matchedString)
    }

    @Test
    fun `parsing example 2`() {
        val input = "@id=234AB :dan!d@localhost PRIVMSG #chan :Hey what's up!\r\n"

        val ast = parser.parse(input)

        val expectedMatchedString = "@id=234AB :dan!d@localhost PRIVMSG #chan :Hey what's up!\r\n"
        assertEquals(expectedMatchedString, ast.matchedString)
    }
}