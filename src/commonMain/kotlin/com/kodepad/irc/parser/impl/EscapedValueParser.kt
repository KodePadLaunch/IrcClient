package com.kodepad.irc.parser.impl

import com.kodepad.irc.parser.Parser
import com.kodepad.irc.parser.Ast
import com.kodepad.irc.parser.ParserFactory
import com.kodepad.irc.logging.LoggerFactory


class EscapedValueParser(parserFactory: ParserFactory): Parser {
    companion object {
        private val logger = LoggerFactory.getLogger(EscapedValueParser::class)
    }

    override fun parse(input: String): Ast {
        logger.debug("input: {}", input)

        TODO("Implement parse")
    }

    override fun toString(): String = "${this::class.simpleName}"
}