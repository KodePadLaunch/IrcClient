package com.kodepad.irc.connection

import com.kodepad.irc.Message
import com.kodepad.irc.event.EventDispatcher
import com.kodepad.irc.event.ExceptionEvent
import com.kodepad.irc.exception.socket.SocketException

class ConnectionExceptionCaptureDecorator(
    private val connection: Connection,
    private val eventDispatcher: EventDispatcher,
): Connection {
    override suspend fun connect() = capture { connection.connect() }
    override suspend fun read() = capture { connection.read() }
    override suspend fun write(message: Message) = capture { connection.write(message) }
    override suspend fun close() = capture { connection.close() }

    private suspend fun<T> capture(operationBlock: suspend () -> T): T {
        return try {
            operationBlock()
        } catch (socketException: SocketException) {
            eventDispatcher.dispatch(ExceptionEvent::class, ExceptionEvent(socketException))
            throw socketException
        }
    }
}