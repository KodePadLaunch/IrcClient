package com.kodepad.irc.parser.impl

import com.kodepad.irc.logging.LoggerFactory
import com.kodepad.irc.parser.Ast
import com.kodepad.irc.parser.Parser
import com.kodepad.irc.parser.ParserFactory
import com.kodepad.irc.parser.StringConstants.COLON
import com.kodepad.irc.parser.StringConstants.SPACE
import com.kodepad.irc.parser.Token


class TrailingParser(private val parserFactory: ParserFactory): Parser {
    companion object {
        private val logger = LoggerFactory.getLogger(TrailingParser::class)
    }

    override fun parse(input: String): Ast {
        logger.debug("input: {}", input)

        val ast = with(parserFactory) {
            getMultipleOccurenceStrategyParser(
                getAnyOneParser(
                    getExactParser(COLON),
                    getExactParser(SPACE),
                    get(NoSpCrLfClParser::class)
                )
            )
        }.parse(input)

        logger.debug("matchedString: {}", ast.matchedString)
        logger.trace("ast: {}", ast)
        return ast.copy(token = Token.Trailing)
    }

    override fun toString(): String = "${this::class.simpleName}"
}