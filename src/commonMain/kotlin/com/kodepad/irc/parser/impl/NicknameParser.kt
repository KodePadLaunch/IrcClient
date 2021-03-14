package com.kodepad.irc.parser.impl

import com.kodepad.irc.parser.Parser
import com.kodepad.irc.parser.StringConstants.DASH
import com.kodepad.irc.parser.Ast
import com.kodepad.irc.parser.ParserFactory
import com.kodepad.irc.logging.LoggerFactory

class NicknameParser(private val parserFactory: ParserFactory): Parser {
    companion object {
        private val logger = LoggerFactory.getLogger(NicknameParser::class)
    }

    override fun parse(input: String): Ast {
        logger.debug("input: {}", input)

        val ast = with(parserFactory) {
            getInlineParser(
                getAnyOneParser(
                    get(LetterParser::class),
                    get(SpecialParser::class)
                ),
                getMultipleOccurenceStrategyParser(
                    getAnyOneParser(
                        get(LetterParser::class),
                        get(DigitParser::class),
                        get(SpecialParser::class),
                        getExactParser(DASH)
                    ),
                    0
                )
            )
        }.parse(input)

        logger.debug("matchedString: {}", ast.matchedString)
        logger.trace("ast: {}", ast)
        return ast
    }

    override fun toString(): String = "${this::class.simpleName}"
}