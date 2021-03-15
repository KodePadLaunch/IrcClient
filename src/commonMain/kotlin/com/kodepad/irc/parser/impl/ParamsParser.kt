package com.kodepad.irc.parser.impl

import com.kodepad.irc.logging.LoggerFactory
import com.kodepad.irc.logging.Markers
import com.kodepad.irc.parser.Ast
import com.kodepad.irc.parser.Parser
import com.kodepad.irc.parser.ParserFactory
import com.kodepad.irc.parser.StringConstants.COLON
import com.kodepad.irc.parser.Token

class ParamsParser(private val parserFactory: ParserFactory): Parser {
    companion object {
        private val logger = LoggerFactory.getLogger(ParamsParser::class)
    }

    override fun parse(input: String): Ast {
        logger.debug(Markers.TOP_LEVEL_PARSER, "input: {}", input)

        val ast = with(parserFactory) {
            getInlineParser(
                getMultipleOccurenceStrategyParser(
                    getInlineParser(
                        get(SpaceParser::class),
                        get(MiddleParser::class)
                    )
                ),
                getOptionalParser(
                    getInlineParser(
                        get(SpaceParser::class),
                        getExactParser(COLON),
                        get(TrailingParser::class)
                    )
                )
            )
        }.parse(input)

        logger.debug(Markers.TOP_LEVEL_PARSER, "matchedString: {}", ast.matchedString)
        logger.trace("ast: {}", ast)
        return ast.copy(token = Token.Params)
    }

    override fun toString(): String = "${this::class.simpleName}"
}