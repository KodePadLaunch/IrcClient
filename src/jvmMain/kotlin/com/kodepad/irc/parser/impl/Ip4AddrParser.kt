package com.kodepad.irc.parser.impl

import com.kodepad.irc.parser.factory.ParserFactory
import com.kodepad.irc.parser.Parser
import com.kodepad.irc.parser.StringConstants.DIGIT
import com.kodepad.irc.parser.StringConstants.DOT
import com.kodepad.irc.parser.ast.Ast
import org.slf4j.Logger
import org.slf4j.LoggerFactory


class Ip4AddrParser(private val parserFactory: ParserFactory): Parser {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(Ip4AddrParser::class.java)
    }

    override fun parse(input: String): Ast {
        logger.debug("input: $input")

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

        logger.debug("ast: $ast")
        return ast
    }

    override fun toString(): String = "${this::class.simpleName}"
}