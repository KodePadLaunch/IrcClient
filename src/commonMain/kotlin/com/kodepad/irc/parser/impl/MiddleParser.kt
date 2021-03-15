package com.kodepad.irc.parser.impl

import com.kodepad.irc.logging.LoggerFactory
import com.kodepad.irc.parser.Ast
import com.kodepad.irc.parser.Parser
import com.kodepad.irc.parser.ParserFactory
import com.kodepad.irc.parser.StringConstants.COLON
import com.kodepad.irc.parser.Token


class MiddleParser(private val parserFactory: ParserFactory): Parser {
    companion object {
        private val logger = LoggerFactory.getLogger(MiddleParser::class)
    }

    override fun parse(input: String): Ast {
        logger.debug("input: {}", input)

        val ast = with(parserFactory) {
            getInlineParser(
                get(NoSpCrLfClParser::class),
                getMultipleOccurenceStrategyParser(
                    getAnyOneParser(
                        getExactParser(COLON),
                        get(NoSpCrLfClParser::class)
                    )
                )
            )
        }.parse(input)

        logger.debug("matchedString: {}", ast.matchedString)
        logger.trace("ast: {}", ast)
        return ast.copy(token = Token.Middle)
    }

    override fun toString(): String = "${this::class.simpleName}"
}