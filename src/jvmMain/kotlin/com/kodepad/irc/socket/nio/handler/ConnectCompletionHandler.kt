package com.kodepad.irc.socket.nio.handler

import com.kodepad.irc.exception.socket.FailedToConnectException
import java.nio.channels.CompletionHandler
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import org.slf4j.LoggerFactory

class ConnectCompletionHandler(private val continuation: Continuation<Unit>): CompletionHandler<Void, Unit> {
    companion object {
        private val logger = LoggerFactory.getLogger(ConnectCompletionHandler::class.java)
    }

    override fun completed(result: Void?, unit: Unit) {
        logger.debug("Continuation handler completed")

        continuation.resume(Unit)
    }

    override fun failed(exception: Throwable?, unit: Unit) {
        logger.debug("Continuation handler failed", exception)

        val exceptionMessage = "Failed to open connection"

        val returnException =
            if (exception != null) {
                FailedToConnectException(
                    exceptionMessage,
                    exception
                )
            } else {
                FailedToConnectException(exceptionMessage)
            }
        continuation.resumeWithException(returnException)
    }
}