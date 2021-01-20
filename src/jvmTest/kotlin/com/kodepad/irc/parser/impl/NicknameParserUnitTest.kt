package com.kodepad.irc.parser.impl

import com.kodepad.irc.parser.factory.ParserAbstractFactoryImpl
import com.kodepad.irc.parser.factory.ParserFactory
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.test.Test
import kotlin.test.assertEquals

class NicknameParserUnitTest {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(NicknameParserUnitTest::class.java)
    }

    private val parserFactory: ParserFactory = ParserAbstractFactoryImpl
    private val parser = parserFactory.get(NicknameParser::class) as NicknameParser

    @Test
    fun `parse dummykodepadnick in dummykodepadnick MODE dummykodepadnick COLON+i`() {
        val input = "dummykodepadnick MODE dummykodepadnick :+i\r\n"

        val ast = parser.parse(input)

        val expectedMatchedString = "dummykodepadnick"
        assertEquals(expectedMatchedString, ast.matchedString)
    }
}