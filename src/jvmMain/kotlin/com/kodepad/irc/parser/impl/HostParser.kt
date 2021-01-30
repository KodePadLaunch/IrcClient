package com.kodepad.irc.parser.impl

import com.kodepad.irc.parser.Parser
import com.kodepad.irc.parser.StringConstants.HOST
import com.kodepad.irc.parser.ast.Ast
import com.kodepad.irc.parser.factory.ParserFactory
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class HostParser(private val parserFactory: ParserFactory): Parser {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(HostParser::class.java)
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