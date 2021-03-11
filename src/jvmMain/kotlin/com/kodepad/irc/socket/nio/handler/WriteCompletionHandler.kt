package com.kodepad.irc.socket.nio.handler

import com.kodepad.irc.exception.socket.EndOfStreamException
import com.kodepad.irc.exception.socket.FailedToConnectException
import com.kodepad.irc.exception.socket.FailedToWriteException
import java.nio.channels.CompletionHandler
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import org.slf4j.LoggerFactory

class WriteCompletionHandler(
    private val continuation: Continuation<Int>,
) : CompletionHandler<Int?, Unit> {
    companion object {
        private val logger = LoggerFactory.getLogger(WriteCompletionHandler::class.java)
    }

    override fun completed(result: Int?, unit: Unit) {
        logger.debug("result: $result")
        val returnResult = result ?: -1
        if (returnResult == -1) {
            continuation.resumeWithException(EndOfStreamException("End of stream reached while writing"))
        }
        continuation.resume(returnResult)
    }

    override fun failed(exc: Throwable?, unit: Unit) {
        logger.debug("writeByteBuffer continuation handler failed", exc)

        val exceptionMessage = "Failed to write byte buffer"
        val exception =
            if (exc != null) FailedToWriteException(exceptionMessage, exc) else FailedToConnectException(
                exceptionMessage
            )
        continuation.resumeWithException(exception)
    }
}