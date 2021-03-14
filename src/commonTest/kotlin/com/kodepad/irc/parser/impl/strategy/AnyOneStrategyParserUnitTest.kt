package com.kodepad.irc.parser.impl.strategy

import com.kodepad.irc.exception.parser.ParsingException
import com.kodepad.irc.logging.Logger
import com.kodepad.irc.logging.LoggerFactory
import com.kodepad.irc.parser.ParserFactory
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class AnyOneStrategyParserUnitTest {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(AnyOneStrategyParserUnitTest::class)
    }

    private val parserFactory: ParserFactory = com.kodepad.irc.parser.ParserFactoryImpl

    @Test
    fun `match C as any one of A b and C`() {
        val input = "C"
        val parser = parserFactory.getAnyOneParser(
            parserFactory.getExactParser("A"),
            parserFactory.getExactParser("b"),
            parserFactory.getExactParser("C")
        )

        val ast = parser.parse(input)

        val expectedMatchedString = "C"
        assertEquals(expectedMatchedString, ast.matchedString)
    }

//    @Test
//    fun `parse dan!d@localhost in dan!d@localhost PRIVMSG #chan COLONHey what's up! as anyone Parser`() {
//        val input = "dan!d@localhost PRIVMSG #chan :Hey what's up!\r\n"
//        val parser = with(parserFactory) {
//            getAnyOneParser(
//                get(ServerNameParser::class),
//                getInlineParser(
//                    get(NicknameParser::class),
//                    getOptionalParser(
//                        getInlineParser(
//                            getOptionalParser(
//                                getInlineParser(
//                                    getExactParser("!"),
//                                    get(UserParser::class)
//                                )
//                            ),
//                            getExactParser("@"),
//                            get(HostParser::class)
//                        )
//                    )
//                )
//            )
//        }
//
//        val ast = parser.parse(input)
//
//        val expectedMatchedString = "dan!d@localhost"
//        assertEquals(expectedMatchedString, ast.matchedString)
//    }

    @Test
    fun `fail to match d as any one of A b and C`() {
        val input = "d"
        val parser = parserFactory.getAnyOneParser(
            parserFactory.getExactParser("A"),
            parserFactory.getExactParser("b"),
            parserFactory.getExactParser("C")
        )

        assertFailsWith<ParsingException>{
            parser.parse(input)
        }.apply {
            logger.info(this.toString())
        }
    }
}