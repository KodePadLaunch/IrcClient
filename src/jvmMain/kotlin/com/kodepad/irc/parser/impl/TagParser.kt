package com.kodepad.irc.parser.impl

import com.kodepad.irc.parser.Parser
import com.kodepad.irc.parser.StringConstants.EQUAL
import com.kodepad.irc.parser.Token
import com.kodepad.irc.parser.ast.Ast
import com.kodepad.irc.parser.factory.ParserFactory
import org.slf4j.Logger
import org.slf4j.LoggerFactory


class TagParser(private val parserFactory: ParserFactory): Parser {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(TagParser::class.java)
    }

    override fun parse(input: String): Ast {
        logger.debug("input: $input")

        val ast = with(parserFactory) {
            getInlineParser(
                get(KeyParser::class),
                getOptionalParser(
                    getInlineParser(
                        getExactParser(EQUAL),
                        get(ValueParser::class)
                    )
                )
            )
        }.parse(input)

        logger.debug("ast: $ast")
        return ast.copy(token = Token.Tag)
    }

    override fun toString(): String = "${this::class.simpleName}"
}