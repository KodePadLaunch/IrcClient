package com.kodepad.irc.connection.impl

import com.kodepad.irc.codec.decoder.Decoder
import com.kodepad.irc.codec.encoder.Encoder
import com.kodepad.irc.connection.Connection
import com.kodepad.irc.dto.Message
import com.kodepad.irc.serdes.deserializer.Deserializer
import com.kodepad.irc.serdes.serializer.Serializer
import com.kodepad.irc.socket.Socket
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import org.slf4j.LoggerFactory

class ConnectionImpl(
    private val socket: Socket,
    private val encoder: Encoder,
    private val decoder: Decoder,
    private val serializer: Serializer<Message>,
    private val deserializer: Deserializer<Message>,
) : Connection {
    companion object {
        private val logger = LoggerFactory.getLogger(ConnectionImpl::class.java)
    }

    override fun read(): Flow<Message> =
        socket.read()
            .flowOn(Dispatchers.IO)
            .map { byteArray -> deserializer deserialize (decoder decode byteArray) }
            .flowOn(Dispatchers.Default)

    override fun write(message: Message) {
        logger.debug("message: {}", message)
        socket.write(encoder encode (serializer serialize message))
    }

    override fun close() {
        socket.close()
    }
}