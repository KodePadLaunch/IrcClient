package com.kodepad.irc.parser.impl

import com.kodepad.irc.parser.factory.ParserFactory
import com.kodepad.irc.parser.ast.Ast
import com.kodepad.irc.parser.Parser
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class PrefixParser(private val parserFactory: ParserFactory) : Parser {
    companion object {
        private val logger: Logger = LoggerFactory.getLogger(PrefixParser::class.java)
    }

    override fun parse(input: String): Ast {
        logger.debug("input: $input")

        val ast = with(parserFactory) {
            getAnyOneParser(
                get(ServerNameParser::class),
                getInlineParser(
                    get(NicknameParser::class),
                    getOptionalParser(
                        getInlineParser(
                            getOptionalParser(
                                getInlineParser(
                                    getExactParser("!"),
                                    get(UserParser::class)
                                )
                            ),
                            getExactParser("@"),
                            get(HostParser::class)
                        )
                    )
                )
            )
        }.parse(input)

        logger.debug("ast: $ast")
        return ast
    }

    override fun toString(): String = "${this::class.simpleName}"
}