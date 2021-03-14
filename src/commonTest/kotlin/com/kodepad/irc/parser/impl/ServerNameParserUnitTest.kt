package com.kodepad.irc.parser.impl

import com.kodepad.irc.logging.Logger
import com.kodepad.irc.logging.LoggerFactory
import com.kodepad.irc.parser.ParserFactory
import kotlin.test.Test
import kotlin.test.assertEquals

class ServerNameParserUnitTest {
    companion object {
        private val logger: Logger = LoggerFactory.getLogger(ServerNameParserUnitTest::class)
    }

    private val parserFactory: ParserFactory = com.kodepad.irc.parser.ParserFactoryImpl
    private val parser = parserFactory.get(ServerNameParser::class) as ServerNameParser

    @Test
    fun `parse ircDOTexampleDOTcom as servername`() {
        val input = "irc.example.com"

        val ast = parser.parse(input)

        val expectedMatchedString = "irc.example.com"
        assertEquals(expectedMatchedString, ast.matchedString)
    }

//    @Test
//    fun `fail to parse dan!d@localhost in dan!d@localhost PRIVMSG #chan COLONHey what's up! as anyone Parser`() {
//        val input = "dan!d@localhost PRIVMSG #chan :Hey what's up!\r\n"
//
//        assertFailsWith<ParsingException>{
//            parser.parse(input)
//        }.apply {
//            logger.info(this.toString())
//        }
//    }

//    @Test
//    fun `fail to parse dan as servername`() {
//        val input = "dan"
//
//        assertFailsWith<ParsingException>{
//            parser.parse(input)
//        }.apply {
//            logger.info(this.toString())
//        }
//    }
}