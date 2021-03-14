package com.kodepad.irc.parser.impl

import com.kodepad.irc.logging.Logger
import com.kodepad.irc.logging.LoggerFactory
import com.kodepad.irc.parser.ParserFactory
import kotlin.test.Test
import kotlin.test.assertEquals

class MessageParserUnitTest {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(MessageParserUnitTest::class)
    }

    private val parserFactory: ParserFactory = com.kodepad.irc.parser.ParserFactoryImpl
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

    @Test
    fun `parsing example 12`() {
        val input = ":freenode-connect!frigg@freenode/utility-bot/frigg NOTICE dummykodepadnick :Welcome to freenode. To protect the network all new connections will be scanned for vulnerabilities. This will not harm your computer, and vulnerable hosts will be notified.\r\n"

        val ast = parser.parse(input)

        val expectedMatchedString = ":freenode-connect!frigg@freenode/utility-bot/frigg NOTICE dummykodepadnick :Welcome to freenode. To protect the network all new connections will be scanned for vulnerabilities. This will not harm your computer, and vulnerable hosts will be notified.\r\n"
        assertEquals(expectedMatchedString, ast.matchedString)
    }

    @Test
    fun `parsing example 13`() {
        val input = ":dummykodepadnick!~ircclient@103.121.152.218 JOIN #ircclienttest\r\n"

        val ast = parser.parse(input)

        val expectedMatchedString = ":dummykodepadnick!~ircclient@103.121.152.218 JOIN #ircclienttest\r\n"
        assertEquals(expectedMatchedString, ast.matchedString)
    }
}