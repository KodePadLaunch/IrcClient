package com.kodepad.irc.connection

import com.kodepad.irc.codec.Codec
import com.kodepad.irc.message.Message
import com.kodepad.irc.serdes.SerDes
import com.kodepad.irc.socket.Socket
import org.slf4j.LoggerFactory

class ConnectionImpl(
    private val socket: Socket,
    private val codec: Codec,
    private val serDes: SerDes<Message>,
) : Connection {
    companion object {
        private val logger = LoggerFactory.getLogger(ConnectionImpl::class.java)
    }

    override suspend fun connect() {
        logger.debug("connect called!")
        socket.open()
    }

    override suspend fun read(): Message {
        val messageByteArray = socket.read()
        val messageString = codec decode messageByteArray
        val message = serDes deserialize messageString

        logger.debug("message: {}", message)

        return message
    }

    override suspend fun write(message: Message) {
        logger.debug("message: {}", message)

        val messageString = serDes serialize message
        val messageByteArray = codec encode messageString

        socket.write(messageByteArray)
    }

    override fun close() {
        socket.close()
    }
}