package com.kodepad.irc.parser.impl

import com.kodepad.irc.logging.Logger
import com.kodepad.irc.logging.LoggerFactory
import com.kodepad.irc.parser.ParserFactory
import kotlin.test.Test
import kotlin.test.assertEquals

class ParamsParserUnitTest {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(ParamsParserUnitTest::class)
    }

    private val parserFactory: ParserFactory = com.kodepad.irc.parser.ParserFactoryImpl
    private val parser = parserFactory.get(ParamsParser::class) as ParamsParser

    @Test
    fun `parse SPACE LS STAR COLONmulti-prefix extended-join sasl as params`() {
        val input = " LS * :multi-prefix extended-join sasl"

        val ast = parser.parse(input)

        val expectedMatchedString = " LS * :multi-prefix extended-join sasl"
        assertEquals(expectedMatchedString, ast.matchedString)
    }
}