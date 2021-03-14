package com.kodepad.irc.parser.impl

import com.kodepad.irc.logging.Logger
import com.kodepad.irc.logging.LoggerFactory
import com.kodepad.irc.parser.ParserFactory
import kotlin.test.Test
import kotlin.test.assertEquals

class NicknameParserUnitTest {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(NicknameParserUnitTest::class)
    }

    private val parserFactory: ParserFactory = com.kodepad.irc.parser.ParserFactoryImpl
    private val parser = parserFactory.get(NicknameParser::class) as NicknameParser

    @Test
    fun `parse dummykodepadnick in dummykodepadnick MODE dummykodepadnick COLON+i`() {
        val input = "dummykodepadnick MODE dummykodepadnick :+i\r\n"

        val ast = parser.parse(input)

        val expectedMatchedString = "dummykodepadnick"
        assertEquals(expectedMatchedString, ast.matchedString)
    }
}