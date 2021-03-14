package com.kodepad.irc.parser.impl

import com.kodepad.irc.parser.Parser
import com.kodepad.irc.parser.Token
import com.kodepad.irc.parser.Ast
import com.kodepad.irc.parser.ParserFactory
import com.kodepad.irc.logging.LoggerFactory


class ValueParser(private val parserFactory: ParserFactory): Parser {
    companion object {
        private val logger = LoggerFactory.getLogger(ValueParser::class)
    }

    override fun parse(input: String): Ast {
        logger.debug("input: {}", input)

        val ast = with(parserFactory) {
            getMultipleOccurenceStrategyParser(
                get(ValueCharParser::class)
            )
        }.parse(input)

        logger.debug("matchedString: {}", ast.matchedString)
        logger.trace("ast: {}", ast)
        return ast.copy(token = Token.Value)
    }

    override fun toString(): String = "${this::class.simpleName}"
}