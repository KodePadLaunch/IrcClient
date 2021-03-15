package com.kodepad.irc.parser.impl.strategy

import com.kodepad.irc.exception.parser.ParsingException
import com.kodepad.irc.logging.Logger
import com.kodepad.irc.logging.LoggerFactory
import com.kodepad.irc.parser.ParserFactory
import com.kodepad.irc.parser.StringConstants.DIGIT
import com.kodepad.irc.parser.impl.HostParser
import com.kodepad.irc.parser.impl.NicknameParser
import com.kodepad.irc.parser.impl.UserParser
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class InlineStrategyParserUnitTest {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(InlineStrategyParserUnitTest::class)
    }

    private val parserFactory: ParserFactory = com.kodepad.irc.parser.ParserFactoryImpl

    @Test
    fun `parse a inline as a`() {
        val input = "a"
        val parser = parserFactory.getInlineParser(
            parserFactory.getExactParser("a"),
        )

        val ast = parser.parse(input)

        val expectedMatchedString = "a"
        assertEquals(expectedMatchedString, ast.matchedString)
    }

    @Test
    fun `parse a colon b inline as aa colon bb`() {
        val input = "a:b"
        val parser = parserFactory.getInlineParser(
            parserFactory.getExactParser("a"),
            parserFactory.getExactParser(":"),
            parserFactory.getExactParser("b"),
        )

        val ast = parser.parse(input)

        val expectedMatchedString = "a:b"
        assertEquals(expectedMatchedString, ast.matchedString)
    }

    @Test
    fun `parse 12 colon 24 inline as 2 digits colon and 2 digits`() {
        val input = "12:24"
        val parser = parserFactory.getInlineParser(
            parserFactory.getMultipleOccurenceStrategyParser(
                parserFactory.getRegexParser(DIGIT),
                1,
                2
            ),
            parserFactory.getExactParser(":"),
            parserFactory.getMultipleOccurenceStrategyParser(
                parserFactory.getRegexParser(DIGIT),
                1,
                2
            )
        )

        val ast = parser.parse(input)

        val expectedMatchedString = "12:24"
        assertEquals(expectedMatchedString, ast.matchedString)
    }

    @Test
    fun `fail to parse 123 colon 24 inline as 2 digits colon and 2 digits`() {
        val input = "123:24"
        val parser = parserFactory.getInlineParser(
            parserFactory.getMultipleOccurenceStrategyParser(
                parserFactory.getRegexParser(DIGIT),
                1,
                2
            ),
            parserFactory.getExactParser(":"),
            parserFactory.getMultipleOccurenceStrategyParser(
                parserFactory.getRegexParser(DIGIT),
                1,
                2
            )
        )

        assertFailsWith<ParsingException> {
            parser.parse(input)
        }.apply {
            logger.info(this.toString())
        }
    }

    @Test
    fun `parse dan!d@localhost in dan!d@localhost PRIVMSG #chan COLONHey what's up! as inline Parser`() {
        val input = "dan!d@localhost PRIVMSG #chan :Hey what's up!\r\n"
        val parser = with(parserFactory) {
            getInlineParser(
                get(NicknameParser::class),
                getOptionalParser(
                    getInlineParser(
                        getOptionalParser(
                            getInlineParser(
                                getExactParser("!"),
                                get(UserParser::class)
                            )
                        ),
                        getExactParser("@"),
                        get(HostParser::class)
                    )
                )
            )
        }

        val ast = parser.parse(input)

        val expectedMatchedString = "dan!d@localhost"
        assertEquals(expectedMatchedString, ast.matchedString)
    }
}