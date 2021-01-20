package com.kodepad.irc.parser.impl

import com.kodepad.irc.parser.Parser
import com.kodepad.irc.parser.StringConstants.DOT
import com.kodepad.irc.parser.StringConstants.SPACE
import com.kodepad.irc.parser.Token
import com.kodepad.irc.parser.ast.Ast
import com.kodepad.irc.parser.factory.ParserFactory
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class PrefixParser(private val parserFactory: ParserFactory) : Parser {
    companion object {
        private val logger: Logger = LoggerFactory.getLogger(PrefixParser::class.java)
    }

    override fun parse(input: String): Ast {
        logger.debug("input: $input")

        // To remove the ambiguity as the irc grammar for prefix is ambiguos
        val ast = if (input.substringBefore(SPACE).contains(DOT)) {
            with(parserFactory) {
                get(ServerNameParser::class)
            }
        } else {
            with(parserFactory) {
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
            }
        }.parse(input)

        logger.debug("ast: $ast")
        return ast.copy(token = Token.Prefix)
    }

    override fun toString(): String = "${this::class.simpleName}"
}