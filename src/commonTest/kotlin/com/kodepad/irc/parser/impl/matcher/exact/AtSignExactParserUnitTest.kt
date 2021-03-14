package com.kodepad.irc.parser.impl.matcher.exact

import com.kodepad.irc.parser.StringConstants.AT
import com.kodepad.irc.parser.Ast
import com.kodepad.irc.exception.parser.ParsingException
import com.kodepad.irc.logging.Logger
import com.kodepad.irc.logging.LoggerFactory
import com.kodepad.irc.parser.ParserFactory
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class AtSignExactParserUnitTest {
    companion object {
        private val logger: Logger = LoggerFactory.getLogger(AtSignExactParserUnitTest::class)
    }

    private val parserFactory: ParserFactory = com.kodepad.irc.parser.ParserFactoryImpl
    private val atSignExactParser = parserFactory.getExactParser(AT)

    @Test
    fun passExample1() {
        val input = "@"
        val output = atSignExactParser.parse(input)
        val expected = Ast("@", ArrayList(), true)

        assertEquals(expected, output)
    }

    @Test
    fun passExample2() {
        val input = "@hello, world!"
        val output = atSignExactParser.parse(input)
        val expected = Ast("@", ArrayList(), true)

        assertEquals(expected, output)
    }

    @Test
    fun failExample1() {
        val input = "#"
        assertFailsWith<ParsingException>{
            atSignExactParser.parse(input)
        }
    }

    @Test
    fun failExample2() {
        val input = "#@"
        assertFailsWith<ParsingException>{
            atSignExactParser.parse(input)
        }
    }
}