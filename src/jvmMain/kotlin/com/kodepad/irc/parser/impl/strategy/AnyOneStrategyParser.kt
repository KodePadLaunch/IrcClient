package com.kodepad.irc.parser.impl.strategy

import com.kodepad.irc.exception.parser.ParsingException
import com.kodepad.irc.parser.Parser
import com.kodepad.irc.parser.ast.Ast
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class AnyOneStrategyParser(private vararg val parsers: Parser): Parser {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(AnyOneStrategyParser::class.java)
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