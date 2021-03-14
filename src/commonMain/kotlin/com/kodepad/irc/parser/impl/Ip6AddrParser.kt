package com.kodepad.irc.parser.impl

import com.kodepad.irc.parser.Parser
import com.kodepad.irc.parser.StringConstants.HEXDIGIT
import com.kodepad.irc.parser.Ast
import com.kodepad.irc.parser.ParserFactory
import com.kodepad.irc.logging.LoggerFactory


class Ip6AddrParser(private val parserFactory: ParserFactory): Parser {
    companion object {
        private val logger = LoggerFactory.getLogger(Ip6AddrParser::class)
    }

    override fun parse(input: String): Ast {
        logger.debug("input: {}", input)

        val ast = with(parserFactory) {
            getAnyOneParser(
                getInlineParser(
                    getMultipleOccurenceStrategyParser(
                        getRegexParser(HEXDIGIT),
                        1
                    ),
                    getMultipleOccurenceStrategyParser(
                        getInlineParser(
                            getExactParser(":"),
                            getMultipleOccurenceStrategyParser(
                                getRegexParser(HEXDIGIT),
                                1
                            )
                        ),
                        7,
                        7
                    )
                ),
                getInlineParser(
                    getExactParser("0:0:0:0:0:"),
                    getAnyOneParser(
                        getExactParser("0"),
                        getExactParser("FFFF")
                    ),
                    getExactParser(":"),
                    get(Ip4AddrParser::class)
                )
            )
        }.parse(input)

        logger.debug("matchedString: {}", ast.matchedString)
        logger.trace("ast: {}", ast)
        return ast
    }

    override fun toString(): String = "${this::class.simpleName}"
}