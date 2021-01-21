package com.kodepad.irc.parser.impl

import com.kodepad.irc.parser.factory.ParserAbstractFactoryImpl
import com.kodepad.irc.parser.factory.ParserFactory
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.test.Test
import kotlin.test.assertEquals

class HostParserUnitTest {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(HostParserUnitTest::class.java)
    }

    private val parserFactory: ParserFactory = ParserAbstractFactoryImpl
    private val parser = parserFactory.get(HostParser::class) as HostParser

    @Test
    fun `parse freenodeSLASHutilityDASHbotSLASHfrigg as Host`() {
        val input = "freenode/utility-bot/frigg NOTICE dummykodepadnick :Welcome to freenode. To protect the network all new connections will be scanned for vulnerabilities. This will not harm your computer, and vulnerable hosts will be notified.\r\n"

        val ast = parser.parse(input)

        val expectedMatchedString = "freenode/utility-bot/frigg"
        assertEquals(expectedMatchedString, ast.matchedString)
    }
}