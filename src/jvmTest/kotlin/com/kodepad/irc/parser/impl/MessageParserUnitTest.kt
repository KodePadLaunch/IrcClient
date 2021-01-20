package com.kodepad.irc.parser.impl

import com.kodepad.irc.parser.factory.ParserAbstractFactoryImpl
import com.kodepad.irc.parser.factory.ParserFactory
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
    fun `parsing example 01`() {
        val input = ":irc.example.com CAP LS * :multi-prefix extended-join sasl\r\n"

        val ast = parser.parse(input)

        val expectedMatchedString = ":irc.example.com CAP LS * :multi-prefix extended-join sasl\r\n"
        assertEquals(expectedMatchedString, ast.matchedString)
    }

    @Test
    fun `parsing example 02`() {
        val input = "@id=234AB :dan!d@localhost PRIVMSG #chan :Hey what's up!\r\n"

        val ast = parser.parse(input)

        val expectedMatchedString = "@id=234AB :dan!d@localhost PRIVMSG #chan :Hey what's up!\r\n"
        assertEquals(expectedMatchedString, ast.matchedString)
    }
    @Test
    fun `parsing example 03`() {
        val input = ":WiZ NICK Kilroy\r\n"

        val ast = parser.parse(input)

        val expectedMatchedString = ":WiZ NICK Kilroy\r\n"
        assertEquals(expectedMatchedString, ast.matchedString)
    }

    @Test
    fun `parsing example 04`() {
        val input = ":dan-!d@localhost QUIT :Quit: Bye for now!\r\n"

        val ast = parser.parse(input)

        val expectedMatchedString = ":dan-!d@localhost QUIT :Quit: Bye for now!\r\n"
        assertEquals(expectedMatchedString, ast.matchedString)
    }

    @Test
    fun `parsing example 05`() {
        val input = ":WiZ JOIN #Twilight_zone\r\n"

        val ast = parser.parse(input)

        val expectedMatchedString = ":WiZ JOIN #Twilight_zone\r\n"
        assertEquals(expectedMatchedString, ast.matchedString)
    }

    @Test
    fun `parsing example 06`() {
        val input = ":dan-!d@localhost JOIN #test\r\n"

        val ast = parser.parse(input)

        val expectedMatchedString = ":dan-!d@localhost JOIN #test\r\n"
        assertEquals(expectedMatchedString, ast.matchedString)
    }

    @Test
    fun `parsing example 07`() {
        val input = ":dan-!d@localhost PART #test\r\n"

        val ast = parser.parse(input)

        val expectedMatchedString = ":dan-!d@localhost PART #test\r\n"
        assertEquals(expectedMatchedString, ast.matchedString)
    }

    @Test
    fun `parsing example 08`() {
        val input = ":dan!~h@localhost MODE #foobar -bl+i *@192.168.0.1\r\n"

        val ast = parser.parse(input)

        val expectedMatchedString = ":dan!~h@localhost MODE #foobar -bl+i *@192.168.0.1\r\n"
        assertEquals(expectedMatchedString, ast.matchedString)
    }

    @Test
    fun `parsing example 09`() {
        val input = ":irc.example.com MODE #foobar +o bunny\r\n"

        val ast = parser.parse(input)

        val expectedMatchedString = ":irc.example.com MODE #foobar +o bunny\r\n"
        assertEquals(expectedMatchedString, ast.matchedString)
    }

    @Test
    fun `parsing example 10`() {
        val input = ":Angel PRIVMSG Wiz :Hello are you receiving this message ?\r\n"

        val ast = parser.parse(input)

        val expectedMatchedString = ":Angel PRIVMSG Wiz :Hello are you receiving this message ?\r\n"
        assertEquals(expectedMatchedString, ast.matchedString)
    }

    @Test
    fun `parsing example 11`() {
        val input = ":dummykodepadnick MODE dummykodepadnick :+i\r\n"

        val ast = parser.parse(input)

        val expectedMatchedString = ":dummykodepadnick MODE dummykodepadnick :+i\r\n"
        assertEquals(expectedMatchedString, ast.matchedString)
    }
}