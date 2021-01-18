package com.kodepad.irc.serdes

import com.kodepad.irc.dto.Message
import com.kodepad.irc.parser.factory.ParserAbstractFactoryImpl
import com.kodepad.irc.parser.impl.MessageParser
import com.kodepad.irc.serdes.deserializer.Deserializer
import com.kodepad.irc.serdes.deserializer.impl.ast.AstBasedMessageDeserializer
import com.kodepad.irc.serdes.serializer.Serializer
import com.kodepad.irc.serdes.serializer.impl.MessageSerializer

object SerdesMessageFactoryImpl: SerdesFactory<Message> {
    override fun getSerializer(): Serializer<Message> {
        return MessageSerializer()
    }

    override fun getDeserializer(): Deserializer<Message> {
        return AstBasedMessageDeserializer(
            ParserAbstractFactoryImpl.get(MessageParser::class)
        )
    }
}