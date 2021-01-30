package com.kodepad.irc.parser.impl.matcher

import com.kodepad.irc.parser.Parser
import com.kodepad.irc.parser.ast.Ast
import com.kodepad.irc.parser.exception.ParsingException
import org.slf4j.Logger
import org.slf4j.LoggerFactory


class RegexParser(private val pattern: String): Parser {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(RegexParser::class.java)
    }

    private val regex = "^$pattern".toRegex()

    override fun parse(input: String): Ast {
        logger.debug("pattern: {}", pattern)
        logger.debug("input: {}", input)

        val matchResult = regex.find(input)
        val match = matchResult?.value?:throw ParsingException("Could not match ${regex.pattern} regex pattern!")

        val ast = Ast(match, emptyList(), true)

        logger.debug("matchedString: {}", ast.matchedString)
        logger.trace("ast: {}", ast)
        return ast
    }

    override fun toString(): String = "${this::class.simpleName}(${pattern})"
}