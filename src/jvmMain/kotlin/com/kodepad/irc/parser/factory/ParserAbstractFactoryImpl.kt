package com.kodepad.irc.parser.factory

import com.kodepad.irc.parser.Parser
import com.kodepad.irc.parser.exception.ParserNotFoundException
import com.kodepad.irc.parser.impl.matcher.ExactParser
import com.kodepad.irc.parser.impl.matcher.RegexParser
import com.kodepad.irc.parser.impl.CommandParser
import com.kodepad.irc.parser.impl.CrLfParser
import com.kodepad.irc.parser.impl.DigitParser
import com.kodepad.irc.parser.impl.EscapedValueParser
import com.kodepad.irc.parser.impl.HostAddrParser
import com.kodepad.irc.parser.impl.HostNameParser
import com.kodepad.irc.parser.impl.HostParser
import com.kodepad.irc.parser.impl.Ip4AddrParser
import com.kodepad.irc.parser.impl.Ip6AddrParser
import com.kodepad.irc.parser.impl.KeyParser
import com.kodepad.irc.parser.impl.LetterParser
import com.kodepad.irc.parser.impl.MessageParser
import com.kodepad.irc.parser.impl.MiddleParser
import com.kodepad.irc.parser.impl.NicknameParser
import com.kodepad.irc.parser.impl.NoSpCrLfClParser
import com.kodepad.irc.parser.impl.ParamsParser
import com.kodepad.irc.parser.impl.PrefixParser
import com.kodepad.irc.parser.impl.ServerNameParser
import com.kodepad.irc.parser.impl.ShortNameParser
import com.kodepad.irc.parser.impl.SpaceParser
import com.kodepad.irc.parser.impl.SpecialParser
import com.kodepad.irc.parser.impl.TagParser
import com.kodepad.irc.parser.impl.TagsParser
import com.kodepad.irc.parser.impl.TrailingParser
import com.kodepad.irc.parser.impl.UserParser
import com.kodepad.irc.parser.impl.ValueCharParser
import com.kodepad.irc.parser.impl.ValueParser
import com.kodepad.irc.parser.impl.VendorParser
import com.kodepad.irc.parser.impl.strategy.AnyOneStrategyParser
import com.kodepad.irc.parser.impl.strategy.InlineStrategyParser
import com.kodepad.irc.parser.impl.strategy.MultipleOccurenceStrategyParser
import kotlin.reflect.KClass

object ParserAbstractFactoryImpl : ParserFactory {
    override fun get(parserKClass: KClass<out Parser>): Parser = when (parserKClass) {
        CommandParser::class -> CommandParser(this)
        EscapedValueParser::class -> EscapedValueParser(this)
        KeyParser::class -> KeyParser(this)
        LetterParser::class -> LetterParser(this)
        MessageParser::class -> MessageParser(this)
        ParamsParser::class -> ParamsParser(this)
        PrefixParser::class -> PrefixParser(this)
        SpaceParser::class -> SpaceParser(this)
        TagParser::class -> TagParser(this)
        TagsParser::class -> TagsParser(this)
        VendorParser::class -> VendorParser(this)
        HostNameParser::class -> HostNameParser(this)
        ServerNameParser::class -> ServerNameParser(this)
        NicknameParser::class -> NicknameParser(this)
        UserParser::class -> UserParser(this)
        HostParser::class -> HostParser(this)
        HostAddrParser::class -> HostAddrParser(this)
        Ip4AddrParser::class -> Ip4AddrParser(this)
        Ip6AddrParser::class -> Ip6AddrParser(this)
        ShortNameParser::class -> ShortNameParser(this)
        DigitParser::class -> DigitParser(this)
        SpecialParser::class -> SpecialParser(this)
        MiddleParser::class -> MiddleParser(this)
        TrailingParser::class -> TrailingParser(this)
        NoSpCrLfClParser::class -> NoSpCrLfClParser(this)
        CrLfParser::class -> CrLfParser(this)
        ValueParser::class -> ValueParser(this)
        ValueCharParser::class -> ValueCharParser(this)
        else -> throw ParserNotFoundException("Parser for $parserKClass not found!")
    }

    override fun getExactParser(terminal: String): Parser = ExactParser(terminal)
    override fun getRegexParser(pattern: String): Parser = RegexParser(pattern)

    override fun getAnyOneParser(vararg parsers: Parser) = AnyOneStrategyParser(*parsers)
    override fun getMultipleOccurenceStrategyParser(parser: Parser, min: Int, max: Int) = MultipleOccurenceStrategyParser(parser, min, max)
    // Optional parser is multiple occurence parser with min 0 and max 1
    override fun getOptionalParser(parser: Parser) = getMultipleOccurenceStrategyParser(parser, 0, 1)
    override fun getInlineParser(vararg parsers: Parser): Parser = InlineStrategyParser(*parsers)
}