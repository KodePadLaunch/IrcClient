package com.kodepad.irc.parser.impl

import com.kodepad.irc.parser.Parser
import com.kodepad.irc.parser.StringConstants.COLON
import com.kodepad.irc.parser.Token
import com.kodepad.irc.parser.ast.Ast
import com.kodepad.irc.parser.factory.ParserFactory
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class ParamsParser(private val parserFactory: ParserFactory): Parser {
    companion object {
        private val logger: Logger = LoggerFactory.getLogger(ParamsParser::class.java)
    }

    override fun parse(input: String): Ast {
        logger.debug("input: $input")

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

        logger.debug("ast: $ast")
        return ast.copy(token = Token.Params)
    }

    override fun toString(): String = "${this::class.simpleName}"
}