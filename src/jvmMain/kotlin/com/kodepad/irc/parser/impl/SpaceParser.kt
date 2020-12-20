package com.kodepad.irc.parser.impl

import com.kodepad.irc.parser.factory.ParserFactory
import com.kodepad.irc.parser.ast.Ast
import com.kodepad.irc.parser.Parser
import com.kodepad.irc.parser.StringConstants.SPACE
import org.slf4j.Logger
import org.slf4j.LoggerFactory


class SpaceParser(private val parserFactory: ParserFactory): Parser {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(SpaceParser::class.java)
    }

    override fun parse(input: String): Ast {
        logger.debug("input: $input")

        val ast = with(parserFactory) {
            getExactParser(SPACE)
        }.parse(input)

        logger.debug("ast: $ast")
        return ast
    }

    override fun toString(): String = "${this::class.simpleName}"
}