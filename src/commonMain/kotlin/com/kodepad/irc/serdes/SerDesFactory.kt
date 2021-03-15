package com.kodepad.irc.serdes

import com.kodepad.irc.exception.serdes.SerdesNotAvailableException
import com.kodepad.irc.Message
import com.kodepad.irc.parser.ParserFactoryImpl
import com.kodepad.irc.parser.impl.MessageParser
import kotlin.reflect.KClass

object SerDesFactory {
    private val parserFactory = ParserFactoryImpl

    fun <T : Any> getSerdes(kClass: KClass<T>): SerDes<T> = when(kClass) {
            Message::class -> SerDesImpl(parserFactory.get(MessageParser::class)) as SerDes<T>
            else -> throw SerdesNotAvailableException("Serdes not available for type $kClass")
        }
}