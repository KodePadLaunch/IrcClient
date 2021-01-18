package com.kodepad.irc.parser.factory

import com.kodepad.irc.parser.Parser
import kotlin.reflect.KClass

interface ParserFactory {
        fun get(parserKClass: KClass<out Parser>): Parser
        fun getExactParser(terminal: String): Parser
        fun getRegexParser(pattern: String): Parser
        fun getAnyOneParser(vararg parsers: Parser): Parser
        fun getMultipleOccurenceStrategyParser(
                parser: Parser,
                min: Int = 0,
                max: Int = Int.MAX_VALUE
        ): Parser
        fun getOptionalParser(parser: Parser): Parser
        fun getInlineParser(vararg parsers: Parser): Parser
}