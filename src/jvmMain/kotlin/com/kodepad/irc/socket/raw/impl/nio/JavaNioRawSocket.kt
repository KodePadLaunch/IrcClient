package com.kodepad.irc.socket.raw.impl.nio

import com.kodepad.irc.socket.raw.RawSocket
import com.kodepad.irc.socket.raw.impl.nio.handler.ConnectCompletionHandler
import com.kodepad.irc.socket.raw.impl.nio.handler.ReadCompletionHandler
import com.kodepad.irc.socket.raw.impl.nio.handler.WriteCompletionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.nio.channels.AsynchronousSocketChannel
import kotlin.coroutines.suspendCoroutine

// todo: Check and properly handle exceptions
class JavaNioRawSocket(
    private val asynchronousSocketChannel: AsynchronousSocketChannel,
    private val hostname: String,
    private val port: Int,
    private val delimiterByteArray: ByteArray
): RawSocket {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(JavaNioRawSocket::class.java)

        const val BUFFER_SIZE_BYTES = 4096
    }

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private val connectJob = coroutineScope.launch {
        suspendCoroutine<Unit> { continuation ->
            val completionCallback = ConnectCompletionHandler(continuation)
            asynchronousSocketChannel.connect(InetSocketAddress(hostname, port), Unit, completionCallback)
        }
    }

    // todo: Break this down into comprehensible chunks
    override fun read(): Flow<ByteArray> = flow {
        connectJob.join()

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
    }

    override suspend fun write(byteArray: ByteArray): Int {
        logger.debug("byteArray: {}", byteArray)

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

    override fun close() {
        coroutineScope.cancel()
        asynchronousSocketChannel.close()
    }

    private suspend fun readByteBuffer(byteBuffer: ByteBuffer) = suspendCoroutine<Int> { continuation ->
        val completionCallback = ReadCompletionHandler(continuation)
        asynchronousSocketChannel.read(byteBuffer, Unit, completionCallback)
    }

    private suspend fun writeByteBuffer(byteBuffer: ByteBuffer) = suspendCoroutine<Int> { continuation ->
        val completionCallback = WriteCompletionHandler(continuation)
        logger.debug("byteBuffer: $byteBuffer")
        asynchronousSocketChannel.write(byteBuffer, Unit, completionCallback)
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
}
