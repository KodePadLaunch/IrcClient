package com.kodepad.irc.parser.impl.strategy

import com.kodepad.irc.exception.parser.ParsingException
import com.kodepad.irc.parser.Parser
import com.kodepad.irc.parser.Ast
import com.kodepad.irc.logging.LoggerFactory

class AnyOneStrategyParser(private vararg val parsers: Parser): Parser {
    companion object {
        private val logger = LoggerFactory.getLogger(AnyOneStrategyParser::class)
    }

    override fun parse(input: String): Ast {
        logger.debug("parsers: {}", parsers.toList())
        logger.debug("input: {}", input)

        parsers.forEach { parser ->
            try {
                val parsedAst = parser.parse(input)

                val ast = Ast(
                    parsedAst.matchedString,
                    arrayListOf(parsedAst),
                    true
                )

                logger.debug("matchedString: {}", ast.matchedString)
                logger.trace("ast: {}", ast)
                return ast
            } catch (parsingException: ParsingException) {}
        }

        throw ParsingException("Could not match for any ${parsers.toList()}")
    }

    override fun toString(): String = "${this::class.simpleName}(${parsers.toList()})"
}