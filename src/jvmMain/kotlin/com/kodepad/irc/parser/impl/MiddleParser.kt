package com.kodepad.irc.parser.impl

import com.kodepad.irc.parser.factory.ParserFactory
import com.kodepad.irc.parser.ast.Ast
import com.kodepad.irc.parser.Parser
import com.kodepad.irc.parser.StringConstants.COLON
import org.slf4j.Logger
import org.slf4j.LoggerFactory


class MiddleParser(private val parserFactory: ParserFactory): Parser {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(MiddleParser::class.java)
    }

    override fun parse(input: String): Ast {
        logger.debug("input: $input")

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

        logger.debug("ast: $ast")
        return ast
    }

    override fun toString(): String = "${this::class.simpleName}"
}