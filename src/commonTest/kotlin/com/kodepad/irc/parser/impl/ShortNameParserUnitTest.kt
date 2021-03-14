package com.kodepad.irc.parser.impl

import com.kodepad.irc.logging.Logger
import com.kodepad.irc.logging.LoggerFactory
import com.kodepad.irc.parser.ParserFactory
import kotlin.test.Test
import kotlin.test.assertEquals

class ShortNameParserUnitTest {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(ShortNameParserUnitTest::class)
    }

    private val parserFactory: ParserFactory = com.kodepad.irc.parser.ParserFactoryImpl
    private val parser = parserFactory.get(ShortNameParser::class) as ShortNameParser

    @Test
    fun `parse irc as shortname`() {
        val input = "irc"

        val ast = parser.parse(input)

        val expectedMatchedString = "irc"
        assertEquals(expectedMatchedString, ast.matchedString)
    }

    @Test
    fun `parse example as shortname`() {
        val input = "example"

        val ast = parser.parse(input)

        val expectedMatchedString = "example"
        assertEquals(expectedMatchedString, ast.matchedString)
    }

    @Test
    fun `parse com as shortname`() {
        val input = "com"

        val ast = parser.parse(input)

        val expectedMatchedString = "com"
        assertEquals(expectedMatchedString, ast.matchedString)
    }
}