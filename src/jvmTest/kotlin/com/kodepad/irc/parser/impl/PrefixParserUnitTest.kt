package com.kodepad.irc.parser.impl

import com.kodepad.irc.parser.factory.ParserFactory
import com.kodepad.irc.parser.factory.ParserAbstractFactoryImpl
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.test.Test
import kotlin.test.assertEquals

class PrefixParserUnitTest {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(PrefixParserUnitTest::class.java)
    }

    private val parserFactory: ParserFactory = ParserAbstractFactoryImpl
    private val parser = parserFactory.get(PrefixParser::class) as PrefixParser

    @Test
    fun `parse ircDOTexampleDOTcom as prefix`() {
        val input = "irc.example.com"

        val ast = parser.parse(input)

        val expectedMatchedString = "irc.example.com"
        assertEquals(expectedMatchedString, ast.matchedString)
    }


    @Test
    fun `parse dan!d@localhost as prefix in dan!d@localhost PRIVMSG #chan COLONHey what's up!`() {
        val input = "dan!d@localhost PRIVMSG #chan :Hey what's up!\r\n"

        val ast = parser.parse(input)

        val expectedMatchedString = "dan!d@localhost"
        assertEquals(expectedMatchedString, ast.matchedString)
    }
}