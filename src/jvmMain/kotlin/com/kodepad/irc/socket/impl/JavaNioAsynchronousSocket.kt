package com.kodepad.irc.socket.impl

import com.kodepad.irc.socket.Socket
import com.kodepad.irc.socket.exception.FailedToConnectException
import com.kodepad.irc.socket.exception.FailedToReadException
import com.kodepad.irc.socket.exception.FailedToWriteException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.nio.channels.AsynchronousSocketChannel
import java.nio.channels.CompletionHandler
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class JavaNioAsynchronousSocket(
    private val hostname: String,
    private val port: Int,
    private val asynchronousSocketChannel: AsynchronousSocketChannel,
    private val delimiterByteArray: ByteArray
) : Socket {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(JavaNioAsynchronousSocket::class.java)

        const val BUFFER_SIZE_BYTES = 4096
    }

    private val coroutineScope = CoroutineScope(EmptyCoroutineContext)

    // todo: learn about scope and launch
    private val connectJob = coroutineScope.launch {
        connect()
    }

    private suspend fun connect() = suspendCoroutine<Unit> { continuation ->
        val completionCallback = object : CompletionHandler<Void, Socket> {
            override fun completed(result: Void?, socket: Socket) {
                logger.debug("Continuation handler completed")

                continuation.resume(Unit)
            }

            override fun failed(exception: Throwable?, socket: Socket) {
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

        asynchronousSocketChannel.connect(InetSocketAddress(hostname, port), this, completionCallback)
    }

    private fun close() {
        asynchronousSocketChannel.close()
    }

    private fun isOpen(): Boolean {
        // todo: Handle exceptions thrown by .remoteAddress call appropriately
        val remoteAddress = asynchronousSocketChannel.remoteAddress

        return remoteAddress != null
    }

    // todo: Break this down into comprehensible chunks
    override suspend fun read(): Flow<ByteArray> {
        connectJob.join()

        return flow {
            val byteArrayList = ArrayList<Byte>()
            val byteBuffer = ByteBuffer.allocate(BUFFER_SIZE_BYTES)
            while (readByteBuffer(byteBuffer) != -1) {
                byteBuffer.flip()
                while (byteBuffer.position() < byteBuffer.limit()) {
                    byteArrayList.add(byteBuffer.get())

                    if (isEndOfDelimiter(byteArrayList, delimiterByteArray)) {
                        logger.debug("byteArrayList.size: {}", byteArrayList.size)
                        emit(byteArrayList.toByteArray())
                        byteArrayList.clear()
                    }
                }
                byteBuffer.clear()
            }

            close()
        }
    }

    private suspend fun readByteBuffer(byteBuffer: ByteBuffer) = suspendCoroutine<Int> { continuation ->
        val completionCallback = object : CompletionHandler<Int?, Socket> {
            override fun completed(result: Int?, socket: Socket) {
                val returnResult = result ?: -1
                logger.debug("returnResult: {}", returnResult)
                if (returnResult == -1) {
                    asynchronousSocketChannel.close()
                }
                continuation.resume(returnResult)
            }

            override fun failed(exc: Throwable?, socket: Socket) {
                logger.debug("readByteBuffer continuation handler failed", exc)

                val exceptionMessage = "Failed to read byte buffer"
                val exception =
                    if (exc != null) FailedToReadException(exceptionMessage, exc) else FailedToConnectException(
                        exceptionMessage
                    )
                continuation.resumeWithException(exception)
            }
        }
        asynchronousSocketChannel.read(byteBuffer, this, completionCallback)
    }

    private fun isEndOfDelimiter(inputArray: ArrayList<Byte>, searchArray: ByteArray): Boolean {
        val inputArraySize = inputArray.size
        val searchArraySize = searchArray.size

        if (searchArraySize <= inputArraySize) {
            for (index in 0 until searchArraySize) {
                if (inputArray[inputArraySize - searchArraySize + index] != searchArray[index]) {
                    return false
                }
            }

            return true
        } else {
            return false
        }
    }

    override suspend fun write(byteArray: ByteArray): Int {
        connectJob.join()

        var byteCount = 0

        val byteBuffer = ByteBuffer.allocate(BUFFER_SIZE_BYTES)
        for (offset in 0..byteArray.size step BUFFER_SIZE_BYTES) {
            val length =
                if (offset + BUFFER_SIZE_BYTES < byteArray.size) (offset + BUFFER_SIZE_BYTES) else byteArray.size
            byteBuffer.put(byteArray, offset, length)
            byteBuffer.flip()
            byteCount += writeByteBuffer(byteBuffer)
        }

        return byteCount
    }

    private suspend fun writeByteBuffer(byteBuffer: ByteBuffer) = suspendCoroutine<Int> { continuation ->
        val completionCallback = object : CompletionHandler<Int?, Socket> {
            override fun completed(result: Int?, socket: Socket) {
                logger.debug("result: $result")
                val returnResult = result ?: -1
                if (returnResult == -1) {
                    asynchronousSocketChannel.close()
                }
                continuation.resume(returnResult)
            }

            override fun failed(exc: Throwable?, socket: Socket) {
                logger.debug("writeByteBuffer continuation handler failed", exc)

                val exceptionMessage = "Failed to write byte buffer"
                val exception =
                    if (exc != null) FailedToWriteException(exceptionMessage, exc) else FailedToConnectException(
                        exceptionMessage
                    )
                continuation.resumeWithException(exception)
            }
        }
        logger.debug("byteBuffer: $byteBuffer")
        asynchronousSocketChannel.write(byteBuffer, this, completionCallback)
    }

    // todo: See this could cause any problems in relasing resources
    protected fun finalize() {
        coroutineScope.cancel()
        close()
    }
}
