package com.kodepad.irc.parser.impl

import com.kodepad.irc.parser.Parser
import com.kodepad.irc.parser.Ast
import com.kodepad.irc.parser.ParserFactory
import com.kodepad.irc.logging.LoggerFactory


class HostAddrParser(private val parserFactory: ParserFactory): Parser {
    companion object {
        private val logger = LoggerFactory.getLogger(HostAddrParser::class)
    }

    override fun parse(input: String): Ast {
        logger.debug("input: {}", input)

        val ast = with(parserFactory) {
            getAnyOneParser(
                get(Ip4AddrParser::class),
                get(Ip6AddrParser::class)
            )
        }.parse(input)

        logger.debug("matchedString: {}", ast.matchedString)
        logger.trace("ast: {}", ast)
        return ast
    }

    override fun toString(): String = "${this::class.simpleName}"
}