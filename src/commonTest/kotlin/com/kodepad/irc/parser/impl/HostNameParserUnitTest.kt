package com.kodepad.irc.parser.impl

import com.kodepad.irc.logging.Logger
import com.kodepad.irc.logging.LoggerFactory
import com.kodepad.irc.parser.ParserFactory
import kotlin.test.Test
import kotlin.test.assertEquals

class HostNameParserUnitTest {
    companion object {
        private val logger: Logger = LoggerFactory.getLogger(HostNameParserUnitTest::class)
    }

    private val parserFactory: ParserFactory = com.kodepad.irc.parser.ParserFactoryImpl
    private val parser = parserFactory.get(HostNameParser::class) as HostNameParser

    @Test
    fun `parse ircDOTexampleDOTcom as hostname`() {
        val input = "irc.example.com"

        val ast = parser.parse(input)

        val expectedMatchedString = "irc.example.com"
        assertEquals(expectedMatchedString, ast.matchedString)
    }
}