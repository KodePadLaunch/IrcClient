package com.kodepad.irc.parser.impl

import com.kodepad.irc.logging.LoggerFactory
import com.kodepad.irc.parser.Ast
import com.kodepad.irc.parser.Parser
import com.kodepad.irc.parser.ParserFactory
import com.kodepad.irc.parser.StringConstants.ALPHA
import com.kodepad.irc.parser.StringConstants.DASH
import com.kodepad.irc.parser.StringConstants.DIGIT
import com.kodepad.irc.parser.StringConstants.FORWARD_SLASH
import com.kodepad.irc.parser.Token


class KeyParser(private val parserFactory: ParserFactory) : Parser {
    companion object {
        private val logger = LoggerFactory.getLogger(KeyParser::class)
    }

    override fun parse(input: String): Ast {
        logger.debug("input: {}", input)

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

        logger.debug("matchedString: {}", ast.matchedString)
        logger.trace("ast: {}", ast)
        return ast.copy(token = Token.Key)
    }

    override fun toString(): String = "${this::class.simpleName}"
}