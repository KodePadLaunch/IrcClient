package com.kodepad.irc.parser.impl.matcher.regex

import com.kodepad.irc.parser.StringConstants.ANY_CHARACTER
import com.kodepad.irc.parser.Ast
import com.kodepad.irc.exception.parser.ParsingException
import com.kodepad.irc.logging.Logger
import com.kodepad.irc.logging.LoggerFactory
import com.kodepad.irc.parser.ParserFactory
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class AnyCharacterRegexParserUnitTest {
    companion object {
        private val logger: Logger = LoggerFactory.getLogger(AnyCharacterRegexParserUnitTest::class)
    }

    private val parserFactory: ParserFactory = com.kodepad.irc.parser.ParserFactoryImpl
    private val hostAnyCharacterRegexParser = parserFactory.getRegexParser(ANY_CHARACTER)

    @Test
    fun passExample1() {
        val input = "0"
        val output = hostAnyCharacterRegexParser.parse(input)
        val expected = Ast("0", ArrayList(), true)

        assertEquals(expected, output)
    }

    @Test
    fun passExample2() {
        val input = "b"
        val output = hostAnyCharacterRegexParser.parse(input)
        val expected = Ast("b", ArrayList(), true)

        assertEquals(expected, output)
    }

    @Test
    fun passExample3() {
        val input = "Z"
        val output = hostAnyCharacterRegexParser.parse(input)
        val expected = Ast("Z", ArrayList(), true)

        assertEquals(expected, output)
    }

    @Test
    fun passExample4() {
        val input = ":"
        val output = hostAnyCharacterRegexParser.parse(input)
        val expected = Ast(":", ArrayList(), true)

        assertEquals(expected, output)
    }

    @Test
    fun passExample5() {
        val input = ":abczABCZ0129"
        val output = hostAnyCharacterRegexParser.parse(input)
        val expected = Ast(":", ArrayList(), true)

        assertEquals(expected, output)
    }

    @Test
    fun failExample1() {
        val input = " "
        assertFailsWith<ParsingException>{
            hostAnyCharacterRegexParser.parse(input)
        }
    }

    @Test
    fun failExample2() {
        val input = " a"
        assertFailsWith<ParsingException>{
            hostAnyCharacterRegexParser.parse(input)
        }
    }
}