package com.kodepad.irc.socket.impl

import com.kodepad.irc.event.bus.EventBus
import com.kodepad.irc.socket.raw.RawSocket
import com.kodepad.irc.socket.Socket
import kotlinx.coroutines.flow.Flow
import org.slf4j.Logger
import org.slf4j.LoggerFactory

// todo: Check and properly handle exceptions
class SocketImpl(
    private val rawSocket: RawSocket,
    private val writeMessageEventBus: EventBus<ByteArray>
): Socket {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(SocketImpl::class.java)
    }

    override fun read(): Flow<ByteArray> = rawSocket.read()

    override fun write(byteArray: ByteArray) {
        logger.debug("byteArray: {}", byteArray)
        writeMessageEventBus.put(byteArray)
    }

    override fun close() {
        rawSocket.close()
    }
}
