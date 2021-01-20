package com.kodepad.irc.parser.impl

import com.kodepad.irc.parser.Parser
import com.kodepad.irc.parser.StringConstants.COLON
import com.kodepad.irc.parser.StringConstants.SPACE
import com.kodepad.irc.parser.Token
import com.kodepad.irc.parser.ast.Ast
import com.kodepad.irc.parser.factory.ParserFactory
import org.slf4j.Logger
import org.slf4j.LoggerFactory


class TrailingParser(private val parserFactory: ParserFactory): Parser {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(TrailingParser::class.java)
    }

    override fun parse(input: String): Ast {
        logger.debug("input: $input")

        val ast = with(parserFactory) {
            getMultipleOccurenceStrategyParser(
                getAnyOneParser(
                    getExactParser(COLON),
                    getExactParser(SPACE),
                    get(NoSpCrLfClParser::class)
                )
            )
        }.parse(input)

        logger.debug("ast: $ast")
        return ast.copy(token = Token.Trailing)
    }

    override fun toString(): String = "${this::class.simpleName}"
}