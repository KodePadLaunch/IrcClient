package com.kodepad.irc.parser.impl

import com.kodepad.irc.logging.Logger
import com.kodepad.irc.logging.LoggerFactory
import com.kodepad.irc.parser.ParserFactory
import kotlin.test.Test
import kotlin.test.assertEquals

class UserParserUnitTest {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(UserParserUnitTest::class)
    }

    private val parserFactory: ParserFactory = com.kodepad.irc.parser.ParserFactoryImpl
    private val parser = parserFactory.get(UserParser::class) as UserParser

    @Test
    fun `parse ~ircclient as user`() {
        val input = "~ircclient@103.121.152.218 JOIN #ircclienttest\r\n"

        val ast = parser.parse(input)

        val expectedMatchedString = "~ircclient"
        assertEquals(expectedMatchedString, ast.matchedString)
    }
}