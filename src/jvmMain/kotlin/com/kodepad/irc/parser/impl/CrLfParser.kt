package com.kodepad.irc.parser.impl

import com.kodepad.irc.parser.Parser
import com.kodepad.irc.parser.StringConstants.CRLF
import com.kodepad.irc.parser.ast.Ast
import com.kodepad.irc.parser.factory.ParserFactory
import org.slf4j.Logger
import org.slf4j.LoggerFactory


class CrLfParser(private val parserFactory: ParserFactory): Parser {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(CrLfParser::class.java)
    }

    override fun parse(input: String): Ast {
        logger.debug("input: $input")

        val ast = with(parserFactory) {
            getExactParser(CRLF)
        }.parse(input)

        logger.debug("ast: $ast")
        return ast
    }

    override fun toString(): String = "${this::class.simpleName}"
}