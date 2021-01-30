package com.kodepad.irc.parser.impl

import com.kodepad.irc.parser.Parser
import com.kodepad.irc.parser.StringConstants.DASH
import com.kodepad.irc.parser.ast.Ast
import com.kodepad.irc.parser.factory.ParserFactory
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class NicknameParser(private val parserFactory: ParserFactory): Parser {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(NicknameParser::class.java)
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