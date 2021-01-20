package com.kodepad.irc.parser.impl

import com.kodepad.irc.parser.exception.ParsingException
import com.kodepad.irc.parser.factory.ParserAbstractFactoryImpl
import com.kodepad.irc.parser.factory.ParserFactory
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class CommandParserUnitTest {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(CommandParserUnitTest::class.java)
    }

    private val parserFactory: ParserFactory = ParserAbstractFactoryImpl
    private val parser = parserFactory.get(CommandParser::class) as CommandParser

    @Test
    fun `parse CAP command`() {
        val input = "CAP LS * :multi-prefix extended-join sasl"

        val ast = parser.parse(input)

        val expectedMatchedString = "CAP"
        assertEquals(expectedMatchedString, ast.matchedString)
    }

    @Test
    fun `parse NICK command`() {
        val input = "NICK Wiz                  ; Requesting the new nick \"Wiz\"."

        val ast = parser.parse(input)

        val expectedMatchedString = "NICK"
        assertEquals(expectedMatchedString, ast.matchedString)
    }

    @Test
    fun `fail to parse 1NICK command`() {
        val input = "1NICK Wiz                  ; Requesting the new nick \"Wiz\"."

        assertFailsWith<ParsingException>{
            parser.parse(input)
        }.apply {
            logger.info(this.toString())
        }
    }
}