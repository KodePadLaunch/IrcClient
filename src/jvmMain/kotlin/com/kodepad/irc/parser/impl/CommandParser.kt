package com.kodepad.irc.parser.impl

import com.kodepad.irc.logging.Markers
import com.kodepad.irc.parser.Parser
import com.kodepad.irc.parser.StringConstants.ALPHA
import com.kodepad.irc.parser.StringConstants.DIGIT
import com.kodepad.irc.parser.Token
import com.kodepad.irc.parser.ast.Ast
import com.kodepad.irc.parser.factory.ParserFactory
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class CommandParser(private val parserFactory: ParserFactory) : Parser {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(CommandParser::class.java)
    }

    override fun parse(input: String): Ast {
        logger.debug(Markers.TOP_LEVEL_PARSER, "input: {}", input)

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

        logger.debug(Markers.TOP_LEVEL_PARSER, "matchedString: {}", ast.matchedString)
        logger.trace("ast: {}", ast)
        return ast.copy(token = Token.Command)
    }

    override fun toString(): String = "${this::class.simpleName}"
}