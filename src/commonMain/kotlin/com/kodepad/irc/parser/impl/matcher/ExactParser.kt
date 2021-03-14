package com.kodepad.irc.parser.impl.matcher

import com.kodepad.irc.exception.parser.ParsingException
import com.kodepad.irc.parser.Parser
import com.kodepad.irc.parser.Ast
import com.kodepad.irc.logging.LoggerFactory


class ExactParser(private val terminal: String): Parser {
    companion object {
        private val logger = LoggerFactory.getLogger(ExactParser::class)
    }

    override fun parse(input: String): Ast {
        logger.debug("terminal: {}", terminal)
        logger.debug("input: {}", input)

        val ast = if (input.startsWith(terminal)) {
            Ast(terminal, emptyList(), true)
        } else {
            throw ParsingException("$terminal missing!")
        }

        logger.debug("matchedString: {}", ast.matchedString)
        logger.trace("ast: {}", ast)
        return ast
    }

    override fun toString(): String = "${this::class.simpleName}(${terminal})"
}