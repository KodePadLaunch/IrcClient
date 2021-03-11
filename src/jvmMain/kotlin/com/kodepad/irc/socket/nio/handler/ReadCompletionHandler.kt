package com.kodepad.irc.socket.nio.handler

import com.kodepad.irc.exception.socket.EndOfStreamException
import com.kodepad.irc.exception.socket.FailedToConnectException
import com.kodepad.irc.exception.socket.FailedToReadException
import java.nio.channels.CompletionHandler
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import org.slf4j.LoggerFactory

class ReadCompletionHandler(
    private val continuation: Continuation<Int>,
) : CompletionHandler<Int?, Unit> {
    companion object {
        private val logger = LoggerFactory.getLogger(ReadCompletionHandler::class.java)
    }

    override fun completed(result: Int?, unit: Unit) {
        val returnResult = result ?: -1
        logger.debug("returnResult: {}", returnResult)
        if (returnResult == -1) {
            continuation.resumeWithException(EndOfStreamException("End of stream reached while reading"))
        }

        continuation.resume(returnResult)
    }

    override fun failed(exc: Throwable?, unit: Unit) {
        logger.debug("readByteBuffer continuation handler failed", exc)

        val exceptionMessage = "Failed to read byte buffer"
        val exception =
            if (exc != null) FailedToReadException(exceptionMessage, exc) else FailedToConnectException(
                exceptionMessage
            )

        continuation.resumeWithException(exception)
    }
}