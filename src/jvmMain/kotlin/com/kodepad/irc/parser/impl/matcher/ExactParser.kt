package com.kodepad.irc.parser.impl.matcher

import com.kodepad.irc.parser.Parser
import com.kodepad.irc.parser.ast.Ast
import com.kodepad.irc.parser.exception.ParsingException
import org.slf4j.Logger
import org.slf4j.LoggerFactory


class ExactParser(private val terminal: String): Parser {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(ExactParser::class.java)
    }

    override fun parse(input: String): Ast {
        logger.debug("terminal: $terminal")
        logger.debug("input: $input")

        val ast = if (input.startsWith(terminal)) {
            Ast(terminal, emptyList(), true)
        } else {
            throw ParsingException("$terminal missing!")
        }

        logger.debug("ast: $ast")
        return ast
    }

    override fun toString(): String = "${this::class.simpleName}(${terminal})"
}