package com.kodepad.irc.parser.impl

import com.kodepad.irc.logging.LoggerFactory
import com.kodepad.irc.parser.Ast
import com.kodepad.irc.parser.Parser
import com.kodepad.irc.parser.ParserFactory
import com.kodepad.irc.parser.StringConstants.DIGIT
import com.kodepad.irc.parser.StringConstants.DOT


class Ip4AddrParser(private val parserFactory: ParserFactory): Parser {
    companion object {
        private val logger = LoggerFactory.getLogger(Ip4AddrParser::class)
    }

    override fun parse(input: String): Ast {
        logger.debug("input: {}", input)

        val ast = with(parserFactory) {
            getInlineParser(
                getMultipleOccurenceStrategyParser(
                    getRegexParser(DIGIT),
                    1,
                    3
                ),
                getExactParser(DOT),
                getMultipleOccurenceStrategyParser(
                    getRegexParser(DIGIT),
                    1,
                    3
                ),
                getExactParser(DOT),
                getMultipleOccurenceStrategyParser(
                    getRegexParser(DIGIT),
                    1,
                    3
                ),
                getExactParser(DOT),
                getMultipleOccurenceStrategyParser(
                    getRegexParser(DIGIT),
                    1,
                    3
                )
            )
        }.parse(input)

        logger.debug("matchedString: {}", ast.matchedString)
        logger.trace("ast: {}", ast)
        return ast
    }

    override fun toString(): String = "${this::class.simpleName}"
}