package com.kodepad.irc.parser.impl

import com.kodepad.irc.logging.LoggerFactory
import com.kodepad.irc.parser.Ast
import com.kodepad.irc.parser.Parser
import com.kodepad.irc.parser.ParserFactory
import com.kodepad.irc.parser.StringConstants.HOST

class HostParser(private val parserFactory: ParserFactory): Parser {
    companion object {
        private val logger = LoggerFactory.getLogger(HostParser::class)
    }

    override fun parse(input: String): Ast {
        logger.debug("input: {}", input)

//        NOTE: Breaking the grammar to improve adaptibility
        val ast = with(parserFactory) {
            getMultipleOccurenceStrategyParser(
                getRegexParser(HOST),
                0
            )
        }.parse(input)

        logger.debug("matchedString: {}", ast.matchedString)
        logger.trace("ast: {}", ast)
        return ast
    }

    override fun toString(): String = "${this::class.simpleName}"
}