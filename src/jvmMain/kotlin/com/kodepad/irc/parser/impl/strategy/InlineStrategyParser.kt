package com.kodepad.irc.parser.impl.strategy

import com.kodepad.irc.parser.Parser
import com.kodepad.irc.parser.ast.Ast
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class InlineStrategyParser(private vararg val parsers: Parser) : Parser {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(InlineStrategyParser::class.java)
    }

    override fun parse(input: String): Ast {
        logger.debug("parsers: ${parsers.toList()}")
        logger.debug("input: $input")

        val parsedProductions = ArrayList<Ast>()
        var parsedLength = 0

        parsers.forEach { parser ->
            val parsedAst = parser.parse(input.substring(parsedLength))

            parsedLength += parsedAst.matchedString.length
            parsedProductions.add(parsedAst)
        }

        val ast = Ast(input.substring(0, parsedLength), parsedProductions, true)

        logger.debug("ast: $ast")

        return ast
    }

    override fun toString(): String = "${this::class.simpleName}(${parsers.toList()})"
}