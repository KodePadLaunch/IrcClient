package com.kodepad.irc.parser.impl

import com.kodepad.irc.parser.factory.ParserFactory
import com.kodepad.irc.parser.ast.Ast
import com.kodepad.irc.parser.Parser
import com.kodepad.irc.parser.StringConstants.SEMICOLON
import com.kodepad.irc.parser.Token
import org.slf4j.Logger
import org.slf4j.LoggerFactory


class TagsParser(private val parserFactory: ParserFactory): Parser {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(TagsParser::class.java)
    }

    override fun parse(input: String): Ast {
        logger.debug("input: $input")

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

        logger.debug("ast: $ast")
        return ast.copy(token = Token.Tags)
    }

    override fun toString(): String = "${this::class.simpleName}"
}