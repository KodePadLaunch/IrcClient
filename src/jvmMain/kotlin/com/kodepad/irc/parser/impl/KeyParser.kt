package com.kodepad.irc.parser.impl

import com.kodepad.irc.parser.StringConstants.ALPHA
import com.kodepad.irc.parser.StringConstants.DIGIT
import com.kodepad.irc.parser.factory.ParserFactory
import com.kodepad.irc.parser.ast.Ast
import com.kodepad.irc.parser.Parser
import com.kodepad.irc.parser.StringConstants.DASH
import com.kodepad.irc.parser.StringConstants.FORWARD_SLASH
import org.slf4j.Logger
import org.slf4j.LoggerFactory


class KeyParser(private val parserFactory: ParserFactory) : Parser {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(KeyParser::class.java)
    }

    override fun parse(input: String): Ast {
        logger.debug("input: $input")

        val ast = with(parserFactory) {
            getInlineParser(
                getOptionalParser(
                    getInlineParser(
                        get(VendorParser::class),
                        getExactParser(FORWARD_SLASH)
                    )
                ),
                getMultipleOccurenceStrategyParser(
                    getAnyOneParser(
                        getRegexParser(ALPHA),
                        getRegexParser(DIGIT),
                        getExactParser(DASH)
                    ),
                    1
                )
            )
        }.parse(input)

        logger.debug("ast: $ast")
        return ast
    }

    override fun toString(): String = "${this::class.simpleName}"
}