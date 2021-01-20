package com.kodepad.irc.parser.impl.strategy

import com.kodepad.irc.parser.StringConstants.ALPHA
import com.kodepad.irc.parser.exception.ParsingException
import com.kodepad.irc.parser.factory.ParserAbstractFactoryImpl
import com.kodepad.irc.parser.factory.ParserFactory
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class MultipleOccurenceStrategyParserUnitTest {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(MultipleOccurenceStrategyParserUnitTest::class.java)
    }

    private val parserFactory: ParserFactory = ParserAbstractFactoryImpl

    @Test
    fun `match exactly 3 occurence of an ALPHABET in ABCD452`() {
        val input = "ABCD452"
        val parser = parserFactory.getMultipleOccurenceStrategyParser(
            parserFactory.getRegexParser(ALPHA),
            3,
            3
        )

        val ast = parser.parse(input)

        val expectedMatchedString = "ABC"
        assertEquals(expectedMatchedString, ast.matchedString)
    }

    @Test
    fun `match atleast 2 and atmost 3 occurence of an ALPHABET in AB452`() {
        val input = "AB452"
        val parser = parserFactory.getMultipleOccurenceStrategyParser(
            parserFactory.getRegexParser(ALPHA),
            2,
            3
        )

        val ast = parser.parse(input)

        val expectedMatchedString = "AB"
        assertEquals(expectedMatchedString, ast.matchedString)
    }

    @Test
    fun `fail to match atleast 2 and atmost 3 occurence of an ALPHABET in A452`() {
        val input = "A452"
        val parser = parserFactory.getMultipleOccurenceStrategyParser(
            parserFactory.getRegexParser(ALPHA),
            2,
            3
        )

        assertFailsWith<ParsingException>{
            parser.parse(input)
        }.apply {
            logger.info(this.toString())
        }
    }

    @Test
    fun `fail to match exactly 3 occurence of an ALPHABET in AB452`() {
        val input = "AB452"
        val parser = parserFactory.getMultipleOccurenceStrategyParser(
            parserFactory.getRegexParser(ALPHA),
            3,
            3
        )

        assertFailsWith<ParsingException>{
            parser.parse(input)
        }.apply {
            logger.info(this.toString())
        }
    }
}