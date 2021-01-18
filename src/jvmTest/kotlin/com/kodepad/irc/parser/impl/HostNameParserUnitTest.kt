package com.kodepad.irc.parser.impl

import com.kodepad.irc.parser.factory.ParserFactory
import com.kodepad.irc.parser.factory.ParserAbstractFactoryImpl
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.test.Test
import kotlin.test.assertEquals

class HostNameParserUnitTest {
    companion object {
        private val logger: Logger = LoggerFactory.getLogger(HostNameParserUnitTest::class.java)
    }

    private val parserFactory: ParserFactory = ParserAbstractFactoryImpl
    private val parser = parserFactory.get(HostNameParser::class) as HostNameParser

    @Test
    fun `parse ircDOTexampleDOTcom as hostname`() {
        val input = "irc.example.com"

        val ast = parser.parse(input)

        val expectedMatchedString = "irc.example.com"
        assertEquals(expectedMatchedString, ast.matchedString)
    }
}