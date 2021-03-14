package com.kodepad.irc.parser.impl

import com.kodepad.irc.parser.Parser
import com.kodepad.irc.parser.StringConstants.USER
import com.kodepad.irc.parser.Ast
import com.kodepad.irc.parser.ParserFactory
import com.kodepad.irc.logging.LoggerFactory

class UserParser(private val parserFactory: ParserFactory): Parser {
    companion object {
        val logger = LoggerFactory.getLogger(UserParser::class)
    }

    override fun parse(input: String): Ast {
        logger.debug("input: {}", input)

        val ast = with(parserFactory) {
            getMultipleOccurenceStrategyParser(
                getRegexParser(USER),
                1
            )
        }.parse(input)

        logger.debug("matchedString: {}", ast.matchedString)
        logger.trace("ast: {}", ast)
        return ast
    }

    override fun toString(): String = "${this::class.simpleName}"
}