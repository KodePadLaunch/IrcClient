package com.kodepad.irc.parser.impl

import com.kodepad.irc.parser.StringConstants.ALPHA
import com.kodepad.irc.parser.StringConstants.DIGIT
import com.kodepad.irc.parser.factory.ParserFactory
import com.kodepad.irc.parser.ast.Ast
import com.kodepad.irc.parser.Parser
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class CommandParser(private val parserFactory: ParserFactory) : Parser {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(CommandParser::class.java)
    }

    override fun parse(input: String): Ast {
        logger.debug("input: $input")
        val ast = with(parserFactory) {
            getAnyOneParser(
                getMultipleOccurenceStrategyParser(
                    getRegexParser(ALPHA),
                    1,
                ),
                getMultipleOccurenceStrategyParser(
                    getRegexParser(DIGIT),
                    3,
                    3
                )
            )
        }.parse(input)

        logger.debug("ast: $ast")
        return ast
    }

    override fun toString(): String = "${this::class.simpleName}"
}