package com.kodepad.irc.socket.nio

import com.kodepad.irc.socket.Socket
import com.kodepad.irc.socket.nio.handler.ConnectCompletionHandler
import com.kodepad.irc.socket.nio.handler.ReadCompletionHandler
import com.kodepad.irc.socket.nio.handler.WriteCompletionHandler
import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.nio.channels.AsynchronousSocketChannel
import kotlin.coroutines.suspendCoroutine
import org.slf4j.Logger
import org.slf4j.LoggerFactory

// todo: Check and properly handle exceptions
class JavaNioSocketImpl(
    private val asynchronousSocketChannel: AsynchronousSocketChannel,
    private val hostname: String,
    private val port: Int,
    private val delimiterByteArray: ByteArray
): Socket {
    companion object {
        private const val BUFFER_SIZE_BYTES = 4096

        private val logger: Logger = LoggerFactory.getLogger(JavaNioSocketImpl::class.java)

    }

    private val inputByteBuffer = ByteBuffer.allocate(BUFFER_SIZE_BYTES)
    private val outputByteBuffer = ByteBuffer.allocate(BUFFER_SIZE_BYTES)
    init {
        inputByteBuffer.flip()
    }

    override suspend fun open() = suspendCoroutine<Unit> { continuation ->
        val completionCallback = ConnectCompletionHandler(continuation)
        asynchronousSocketChannel.connect(InetSocketAddress(hostname, port), Unit, completionCallback)
    }

    // todo: Break this down into comprehensible chunks
    override suspend fun read(): ByteArray {
        val byteArrayList = ArrayList<Byte>()
        while (!isEndOfDelimiter(byteArrayList)) {
            if(inputByteBuffer.position() < inputByteBuffer.limit()) {
                byteArrayList.add(inputByteBuffer.get())
            }
            else{
                // todo: throw exception if bytesRead is negative and also wrap the nio exceptions into irc socket exceptions
                inputByteBuffer.clear()
                val bytesRead = readByteBuffer()
                inputByteBuffer.flip()
            }
        }

        logger.debug("byteArrayList: $byteArrayList")

        return byteArrayList.toByteArray()
    }

    override suspend fun write(byteArray: ByteArray): Int {
        logger.debug("write called!")
        logger.debug("byteArray: ${byteArray.toList()}")

        outputByteBuffer.clear()

        var byteCount = 0
        for (offset in 0..byteArray.size step BUFFER_SIZE_BYTES) {
            val length =
                if (offset + BUFFER_SIZE_BYTES < byteArray.size) BUFFER_SIZE_BYTES else (byteArray.size - offset)
            outputByteBuffer.put(byteArray, offset, length)
            outputByteBuffer.flip()
            byteCount += writeByteBuffer()
        }
        return byteCount
    }

    override suspend fun close() {
        asynchronousSocketChannel.close()
    }

    private suspend fun readByteBuffer() = suspendCoroutine<Int> { continuation ->
        val completionCallback = ReadCompletionHandler(continuation)
        asynchronousSocketChannel.read(inputByteBuffer, Unit, completionCallback)
    }

    private suspend fun writeByteBuffer() = suspendCoroutine<Int> { continuation ->
        val completionCallback = WriteCompletionHandler(continuation)
        asynchronousSocketChannel.write(outputByteBuffer, Unit, completionCallback)
    }

    private fun isEndOfDelimiter(inputArray: ArrayList<Byte>): Boolean {
        if (delimiterByteArray.size <= inputArray.size) {
            for (index in delimiterByteArray.indices) {
                if (inputArray[inputArray.size - delimiterByteArray.size + index] != delimiterByteArray[index]) {
                    return false
                }
            }

            return true
        }
        else {
            return false
        }
    }
}
