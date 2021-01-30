package com.kodepad.irc.parser.impl.strategy

import com.kodepad.irc.parser.Parser
import com.kodepad.irc.parser.ast.Ast
import com.kodepad.irc.parser.exception.ParsingException
import org.slf4j.Logger
import org.slf4j.LoggerFactory

open class MultipleOccurenceStrategyParser(
    private val parser: Parser,
    private val min: Int = 0,
    private val max: Int = Int.MAX_VALUE
): Parser {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(MultipleOccurenceStrategyParser::class.java)
    }

    override fun parse(input: String): Ast {
        logger.debug("({}, {}, {})", parser, min, max)
        logger.debug("input: {}", input)

        val parsedProductions = ArrayList<Ast>()
        var parsedLength = 0

        for(i in 0 until max) {
            val parsedAst = try {
                parser.parse(input.substring(parsedLength))

            } catch (parsingException: ParsingException) {
                if(i < min) {
                    throw ParsingException("Could not find required number of occurence", parsingException)
                }
                else {
                    break
                }
            }

            parsedLength += parsedAst.matchedString.length
            parsedProductions.add(parsedAst)
        }

        val ast = Ast(input.substring(0, parsedLength), parsedProductions, true)

        logger.debug("matchedString: {}", ast.matchedString)
        logger.trace("ast: {}", ast)
        return ast
    }

    override fun toString(): String = "${this::class.simpleName}($parser, $min, $max)"
}