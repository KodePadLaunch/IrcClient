package com.kodepad.irc.parser.impl.matcher.regex

import com.kodepad.irc.parser.StringConstants.DIGIT
import com.kodepad.irc.parser.ast.Ast
import com.kodepad.irc.parser.exception.ParsingException
import com.kodepad.irc.parser.factory.ParserAbstractFactoryImpl
import com.kodepad.irc.parser.factory.ParserFactory
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class DigitRegexParserUnitTest {
    companion object {
        private val logger: Logger = LoggerFactory.getLogger(DigitRegexParserUnitTest::class.java)
    }

    private val parserFactory: ParserFactory = ParserAbstractFactoryImpl
    private val digitRegexParser = parserFactory.getRegexParser(DIGIT)

    @Test
    fun passExample1() {
        val input = "0"
        val output = digitRegexParser.parse(input)
        val expected = Ast("0", ArrayList(), true)

        assertEquals(expected, output)
    }

    @Test
    fun passExample2() {
        val input = "3"
        val output = digitRegexParser.parse(input)
        val expected = Ast("3", ArrayList(), true)

        assertEquals(expected, output)
    }

    @Test
    fun passExample3() {
        val input = "9"
        val output = digitRegexParser.parse(input)
        val expected = Ast("9", ArrayList(), true)

        assertEquals(expected, output)
    }

    @Test
    fun passExample4() {
        val input = "0123456789"
        val output = digitRegexParser.parse(input)
        val expected = Ast("0", ArrayList(), true)

        assertEquals(expected, output)
    }

    @Test
    fun failExample1() {
        val input = "#"
        assertFailsWith<ParsingException>{
            digitRegexParser.parse(input)
        }
    }

    @Test
    fun failExample2() {
        val input = "#0"
        assertFailsWith<ParsingException>{
            digitRegexParser.parse(input)
        }
    }
}