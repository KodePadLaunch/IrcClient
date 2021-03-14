package com.kodepad.irc.parser.impl

import com.kodepad.irc.logging.Markers
import com.kodepad.irc.parser.Parser
import com.kodepad.irc.parser.StringConstants.SEMICOLON
import com.kodepad.irc.parser.Token
import com.kodepad.irc.parser.Ast
import com.kodepad.irc.parser.ParserFactory
import com.kodepad.irc.logging.LoggerFactory


class TagsParser(private val parserFactory: ParserFactory): Parser {
    companion object {
        val logger = LoggerFactory.getLogger(TagsParser::class)
    }

    override fun parse(input: String): Ast {
        logger.debug(Markers.TOP_LEVEL_PARSER, "input: {}", input)

        val ast = with(parserFactory) {
            getInlineParser(
                get(TagParser::class),
                getMultipleOccurenceStrategyParser(
                    getInlineParser(
                        getExactParser(SEMICOLON),
                        get(TagParser::class)
                    )
                )
            )
        }.parse(input)

        logger.debug(Markers.TOP_LEVEL_PARSER, "matchedString: {}", ast.matchedString)
        logger.trace("ast: {}", ast)
        return ast.copy(token = Token.Tags)
    }

    override fun toString(): String = "${this::class.simpleName}"
}