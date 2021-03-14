package com.kodepad.irc.parser.impl.matcher.regex

import com.kodepad.irc.parser.StringConstants.USER
import com.kodepad.irc.logging.Logger
import com.kodepad.irc.logging.LoggerFactory
import com.kodepad.irc.parser.ParserFactory
import kotlin.test.Test
import kotlin.test.assertEquals

class UserRegexParserUnitTest {
    companion object {
        private val logger: Logger = LoggerFactory.getLogger(UserRegexParserUnitTest::class)
    }

    private val parserFactory: ParserFactory = com.kodepad.irc.parser.ParserFactoryImpl
    private val userRegexParser = parserFactory.getRegexParser(USER)

    @Test
    fun passExample1() {
        val input = "~"
        val ast = userRegexParser.parse(input)

        val expectedMatchedString = "~"
        assertEquals(expectedMatchedString, ast.matchedString)
    }
}