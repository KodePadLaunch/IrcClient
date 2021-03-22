package com.kodepad.irc.connection

import com.kodepad.irc.Message
import com.kodepad.irc.codec.CodecFactory
import com.kodepad.irc.codec.Encoding
import com.kodepad.irc.event.EventDispatcherImpl
import com.kodepad.irc.event.EventListener
import com.kodepad.irc.event.ExceptionEvent
import com.kodepad.irc.exception.IrcClientTestException
import com.kodepad.irc.exception.socket.FailedToConnectException
import com.kodepad.irc.exception.socket.FailedToReadException
import com.kodepad.irc.exception.socket.NotYetConnectedException
import com.kodepad.irc.exception.socket.SocketException
import com.kodepad.irc.logging.LoggerFactory
import com.kodepad.irc.logging.Markers.TEST_FLOW
import com.kodepad.irc.serdes.SerDesFactory
import com.kodepad.irc.socket.Socket
import com.kodepad.irc.state.MutableNetworkState
import com.kodepad.kotlinx.coroutines.runBlockingTest
import kotlin.test.Test
import kotlin.test.assertEquals

class ConnectionExceptionCaptureDecoratorUnitTest {
    companion object {
        val logger = LoggerFactory.getLogger(ConnectionExceptionCaptureDecoratorUnitTest::class)
    }

    private val mockConnection = object: Connection {
        override suspend fun connect() {
            throw FailedToConnectException("Failed to connect!!")
        }

        override suspend fun read(): Message {
            throw FailedToReadException("Failed to read!!")
        }

        override suspend fun write(message: Message) {
            throw NotYetConnectedException("Not yet connected!!")
        }

        override suspend fun close() {

        }

    }

    @Test
    fun `try capturing a socket exception`() {
        var eventCaptureCount = 0

        val eventDispatcher = EventDispatcherImpl()
        eventDispatcher.addListener(ExceptionEvent::class, object: EventListener<ExceptionEvent>{
            override fun onEvent(event: ExceptionEvent) {
                logger.debug(TEST_FLOW, "event: $event")
                eventCaptureCount++
            }
        })
        val mutableNetworkState = MutableNetworkState()
        val decoratedConnection = ConnectionExceptionCaptureDecorator(mockConnection, eventDispatcher, mutableNetworkState)

        runBlockingTest {
            ignoreSocketException { decoratedConnection.connect() }
            ignoreSocketException { decoratedConnection.read() }
            ignoreSocketException { decoratedConnection.write(
                Message(
                    tags=null,
                    source="MidKnightKoder!677998db@103.121.152.219",
                    command="PRIVMSG",
                    parameters=listOf(
                        "#ircclienttest",
                        "hello, world!"
                    )
                )
            ) }
            decoratedConnection.close()
        }

        val expectedEventCaptureCount = 3
        assertEquals(expectedEventCaptureCount, eventCaptureCount)
    }

    private suspend fun ignoreSocketException(operationBlock: suspend () -> Unit) {
        try {
            operationBlock()
        } catch (socketException: SocketException) {}
    }
}