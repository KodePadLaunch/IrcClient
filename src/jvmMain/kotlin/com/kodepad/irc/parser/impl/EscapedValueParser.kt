package com.kodepad.irc.parser.impl

import com.kodepad.irc.parser.factory.ParserFactory
import com.kodepad.irc.parser.ast.Ast
import com.kodepad.irc.parser.Parser
import org.slf4j.Logger
import org.slf4j.LoggerFactory


class EscapedValueParser(parserFactory: ParserFactory): Parser {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(EscapedValueParser::class.java)
    }

    override fun parse(input: String): Ast {
        logger.debug("input: $input")
        TODO("Implement parse")
    }

    override fun toString(): String = "${this::class.simpleName}"
}