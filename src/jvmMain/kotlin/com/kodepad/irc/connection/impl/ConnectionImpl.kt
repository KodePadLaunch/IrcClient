package com.kodepad.irc.connection.impl

import com.kodepad.irc.codec.decoder.Decoder
import com.kodepad.irc.codec.encoder.Encoder
import com.kodepad.irc.connection.Connection
import com.kodepad.irc.dto.Message
import com.kodepad.irc.serdes.deserializer.Deserializer
import com.kodepad.irc.serdes.serializer.Serializer
import com.kodepad.irc.socket.Socket
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ConnectionImpl(
    private val socket: Socket,
    private val encoder: Encoder,
    private val decoder: Decoder,
    private val serializer: Serializer<Message>,
    private val deserializer: Deserializer<Message>,
) : Connection {
    override suspend fun read(): Flow<Message> = socket.read().map { byteArray ->
        deserializer deserialize (decoder decode byteArray)
    }

    override suspend fun write(message: Message): Int = socket.write(encoder encode (serializer serialize message))
}