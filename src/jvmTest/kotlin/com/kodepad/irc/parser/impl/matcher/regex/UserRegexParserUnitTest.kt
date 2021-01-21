package com.kodepad.irc.parser.impl.matcher.regex

import com.kodepad.irc.parser.StringConstants.USER
import com.kodepad.irc.parser.factory.ParserAbstractFactoryImpl
import com.kodepad.irc.parser.factory.ParserFactory
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.test.Test
import kotlin.test.assertEquals

class UserRegexParserUnitTest {
    companion object {
        private val logger: Logger = LoggerFactory.getLogger(UserRegexParserUnitTest::class.java)
    }

    private val parserFactory: ParserFactory = ParserAbstractFactoryImpl
    private val userRegexParser = parserFactory.getRegexParser(USER)

    @Test
    fun passExample1() {
        val input = "~"
        val ast = userRegexParser.parse(input)

        val expectedMatchedString = "~"
        assertEquals(expectedMatchedString, ast.matchedString)
    }
}