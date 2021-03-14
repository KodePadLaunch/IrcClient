package com.kodepad.irc.parser.impl

import com.kodepad.irc.exception.parser.ParsingException
import com.kodepad.irc.logging.Logger
import com.kodepad.irc.logging.LoggerFactory
import com.kodepad.irc.parser.ParserFactory
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class CommandParserUnitTest {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(CommandParserUnitTest::class)
    }

    private val parserFactory: ParserFactory = com.kodepad.irc.parser.ParserFactoryImpl
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