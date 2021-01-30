package com.kodepad.irc.parser.impl

import com.kodepad.irc.logging.Markers
import com.kodepad.irc.parser.Parser
import com.kodepad.irc.parser.StringConstants.AT
import com.kodepad.irc.parser.StringConstants.COLON
import com.kodepad.irc.parser.StringConstants.SPACE
import com.kodepad.irc.parser.ast.Ast
import com.kodepad.irc.parser.factory.ParserFactory
import org.slf4j.Logger
import org.slf4j.LoggerFactory


class MessageParser(private val parserFactory: ParserFactory): Parser {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(MessageParser::class.java)
    }

    override fun parse(input: String): Ast {
        logger.debug(Markers.TOP_LEVEL_PARSER, "INPUT: {}", input)

        val ast = with(parserFactory) {
            getInlineParser(
                getOptionalParser(
                    getInlineParser(
                        getExactParser(AT),
                        get(TagsParser::class),
                        getExactParser(SPACE)
                    )
                ),
                getOptionalParser(
                    getInlineParser(
                        getExactParser(COLON),
                        get(PrefixParser::class),
                        getExactParser(SPACE)
                    )
                ),
                get(CommandParser::class),
                getOptionalParser(
                    get(ParamsParser::class)
                ),
                get(CrLfParser::class)
            )
        }.parse(input)

        logger.debug(Markers.TOP_LEVEL_PARSER, "matchedString: {}", ast.matchedString)
        logger.trace("ast: {}", ast)
        return ast
    }

    override fun toString(): String = "${this::class.simpleName}"
}