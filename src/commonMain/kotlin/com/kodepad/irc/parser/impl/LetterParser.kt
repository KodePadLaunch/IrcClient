package com.kodepad.irc.parser.impl

import com.kodepad.irc.logging.LoggerFactory
import com.kodepad.irc.parser.Ast
import com.kodepad.irc.parser.Parser
import com.kodepad.irc.parser.ParserFactory
import com.kodepad.irc.parser.StringConstants.ALPHA


class LetterParser(private val parserFactory: ParserFactory): Parser {
    companion object {
        val logger = LoggerFactory.getLogger(LetterParser::class)
    }

    override fun parse(input: String): Ast {
        logger.debug("input: {}", input)

        val ast = with(parserFactory) {
            getRegexParser(ALPHA)
        }.parse(input)

        logger.debug("matchedString: {}", ast.matchedString)
        logger.trace("ast: {}", ast)
        return ast
    }

    override fun toString(): String = "${this::class.simpleName}"
}