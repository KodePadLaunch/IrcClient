package com.kodepad.irc.parser.impl

import com.kodepad.irc.parser.factory.ParserFactory
import com.kodepad.irc.parser.Parser
import com.kodepad.irc.parser.ast.Ast
import org.slf4j.Logger
import org.slf4j.LoggerFactory


class ShortNameParser(private val parserFactory: ParserFactory): Parser {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(ShortNameParser::class.java)
    }

    override fun parse(input: String): Ast {
        logger.debug("input: $input")

        val ast = with(parserFactory) {
            getInlineParser(
                getAnyOneParser(
                    get(LetterParser::class),
                    get(DigitParser::class)
                ),
                getMultipleOccurenceStrategyParser(
                    getAnyOneParser(
                        get(LetterParser::class),
                        get(DigitParser::class),
                        getExactParser("-")
                    )
                ),
                getMultipleOccurenceStrategyParser(
                    getAnyOneParser(
                        get(LetterParser::class),
                        get(DigitParser::class)
                    )
                )
            )
        }.parse(input)

        logger.debug("ast: $ast")
        return ast
    }

    override fun toString(): String = "${this::class.simpleName}"
}