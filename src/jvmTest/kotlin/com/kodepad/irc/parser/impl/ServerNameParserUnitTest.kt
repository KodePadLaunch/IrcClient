package com.kodepad.irc.parser.impl

import com.kodepad.irc.parser.exception.ParsingException
import com.kodepad.irc.parser.factory.ParserFactory
import com.kodepad.irc.parser.factory.ParserAbstractFactoryImpl
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ServerNameParserUnitTest {
    companion object {
        private val logger: Logger = LoggerFactory.getLogger(ServerNameParserUnitTest::class.java)
    }

    private val parserFactory: ParserFactory = ParserAbstractFactoryImpl
    private val parser = parserFactory.get(ServerNameParser::class) as ServerNameParser

    @Test
    fun `parse ircDOTexampleDOTcom as servername`() {
        val input = "irc.example.com"

        val ast = parser.parse(input)

        val expectedMatchedString = "irc.example.com"
        assertEquals(expectedMatchedString, ast.matchedString)
    }

    @Test
    fun `fail to parse dan!d@localhost in dan!d@localhost PRIVMSG #chan COLONHey what's up! as anyone Parser`() {
        val input = "dan!d@localhost PRIVMSG #chan :Hey what's up!\r\n"

        assertFailsWith<ParsingException>{
            parser.parse(input)
        }.apply {
            logger.info(this.toString())
        }
    }
}