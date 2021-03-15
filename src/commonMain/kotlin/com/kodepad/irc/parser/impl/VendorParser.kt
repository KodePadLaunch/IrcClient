package com.kodepad.irc.parser.impl

import com.kodepad.irc.logging.LoggerFactory
import com.kodepad.irc.parser.Ast
import com.kodepad.irc.parser.Parser
import com.kodepad.irc.parser.ParserFactory


class VendorParser(private val parserFactory: ParserFactory): Parser {
    companion object {
        private val logger = LoggerFactory.getLogger(VendorParser::class)
    }

    override fun parse(input: String): Ast {
        logger.debug("input: {}", input)

        val ast = with(parserFactory) {
            get(HostNameParser::class)
        }.parse(input)

        logger.debug("matchedString: {}", ast.matchedString)
        logger.trace("ast: {}", ast)
        return ast
    }

    override fun toString(): String = "${this::class.simpleName}"
}