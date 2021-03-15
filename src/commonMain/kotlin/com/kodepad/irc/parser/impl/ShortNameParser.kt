package com.kodepad.irc.parser.impl

import com.kodepad.irc.logging.LoggerFactory
import com.kodepad.irc.parser.Ast
import com.kodepad.irc.parser.Parser
import com.kodepad.irc.parser.ParserFactory


class ShortNameParser(private val parserFactory: ParserFactory): Parser {
    companion object {
        private val logger = LoggerFactory.getLogger(ShortNameParser::class)
    }

    override fun parse(input: String): Ast {
        logger.debug("input: {}", input)

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
                        getExactParser("-"),
                    // NOTE: The below parser additon is not part of the IRC spec, it's added to parse things like freenode/utility-bot/frigg as host
                        getExactParser("/")
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

        logger.debug("matchedString: {}", ast.matchedString)
        logger.trace("ast: {}", ast)
        return ast
    }

    override fun toString(): String = "${this::class.simpleName}"
}