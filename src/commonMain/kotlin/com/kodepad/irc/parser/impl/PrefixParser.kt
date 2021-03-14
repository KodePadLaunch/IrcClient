package com.kodepad.irc.parser.impl

import com.kodepad.irc.exception.parser.ParsingException
import com.kodepad.irc.logging.Markers
import com.kodepad.irc.parser.Parser
import com.kodepad.irc.parser.StringConstants.SPACE
import com.kodepad.irc.parser.Token
import com.kodepad.irc.parser.Ast
import com.kodepad.irc.parser.ParserFactory
import com.kodepad.irc.logging.LoggerFactory

// TODO: This parser is very dicey, try to do something better
class PrefixParser(private val parserFactory: ParserFactory) : Parser {
    companion object {
        private val logger = LoggerFactory.getLogger(PrefixParser::class)
    }

    override fun parse(input: String): Ast {
        logger.debug(Markers.TOP_LEVEL_PARSER, "input: {}", input)

        val prefixCandidateString = input.substringBefore(SPACE)

        try {
            val ast = with(parserFactory) {
                get(ServerNameParser::class)
            }.parse(prefixCandidateString)

            if(prefixCandidateString == ast.matchedString) {
                logger.debug(Markers.TOP_LEVEL_PARSER, "matchedString: {}", ast.matchedString)
                logger.trace("ast: {}", ast)
                return ast.copy(token = Token.Prefix)
            }
        } catch (parsingException: ParsingException) {}

        try {
            val ast = with(parserFactory) {
                    getInlineParser(
                        get(NicknameParser::class),
                        getOptionalParser(
                            getInlineParser(
                                getOptionalParser(
                                    getInlineParser(
                                        getExactParser("!"),
                                        get(UserParser::class)
                                    )
                                ),
                                getExactParser("@"),
                                get(HostParser::class)
                            )
                        )
                    )
            }.parse(prefixCandidateString)

            if(prefixCandidateString == ast.matchedString) {
                logger.debug(Markers.TOP_LEVEL_PARSER, "matchedString: {}", ast.matchedString)
                logger.trace("ast: {}", ast)
                return ast.copy(token = Token.Prefix)
            }
        } catch (parsingException: ParsingException) { }

        throw ParsingException("Prefix wasn't completely match, could be a bug in parser")
    }

    override fun toString(): String = "${this::class.simpleName}"
}