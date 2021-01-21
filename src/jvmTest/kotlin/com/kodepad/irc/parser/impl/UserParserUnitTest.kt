package com.kodepad.irc.parser.impl

import com.kodepad.irc.parser.factory.ParserAbstractFactoryImpl
import com.kodepad.irc.parser.factory.ParserFactory
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.test.Test
import kotlin.test.assertEquals

class UserParserUnitTest {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(UserParserUnitTest::class.java)
    }

    private val parserFactory: ParserFactory = ParserAbstractFactoryImpl
    private val parser = parserFactory.get(UserParser::class) as UserParser

    @Test
    fun `parse ~ircclient as user`() {
        val input = "~ircclient@103.121.152.218 JOIN #ircclienttest\r\n"

        val ast = parser.parse(input)

        val expectedMatchedString = "~ircclient"
        assertEquals(expectedMatchedString, ast.matchedString)
    }
}