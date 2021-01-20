package com.kodepad.irc.parser.impl

import com.kodepad.irc.parser.factory.ParserAbstractFactoryImpl
import com.kodepad.irc.parser.factory.ParserFactory
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.test.Test
import kotlin.test.assertEquals

class ShortNameParserUnitTest {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(ShortNameParserUnitTest::class.java)
    }

    private val parserFactory: ParserFactory = ParserAbstractFactoryImpl
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