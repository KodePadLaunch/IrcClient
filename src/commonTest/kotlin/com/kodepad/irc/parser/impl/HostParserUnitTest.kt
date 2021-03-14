package com.kodepad.irc.parser.impl

import com.kodepad.irc.logging.Logger
import com.kodepad.irc.logging.LoggerFactory
import com.kodepad.irc.parser.ParserFactory
import kotlin.test.Test
import kotlin.test.assertEquals

class HostParserUnitTest {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(HostParserUnitTest::class)
    }

    private val parserFactory: ParserFactory = com.kodepad.irc.parser.ParserFactoryImpl
    private val parser = parserFactory.get(HostParser::class) as HostParser

    @Test
    fun `parse freenodeSLASHutilityDASHbotSLASHfrigg as Host`() {
        val input = "freenode/utility-bot/frigg NOTICE dummykodepadnick :Welcome to freenode. To protect the network all new connections will be scanned for vulnerabilities. This will not harm your computer, and vulnerable hosts will be notified.\r\n"

        val ast = parser.parse(input)

        val expectedMatchedString = "freenode/utility-bot/frigg"
        assertEquals(expectedMatchedString, ast.matchedString)
    }
}