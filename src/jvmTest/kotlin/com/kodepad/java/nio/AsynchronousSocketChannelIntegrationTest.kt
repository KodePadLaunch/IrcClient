package com.kodepad.java.nio

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.nio.channels.AsynchronousSocketChannel
import java.nio.charset.Charset
import kotlin.test.Ignore
import kotlin.test.Test


class AsynchronousSocketChannelIntegrationTest {
    companion object {
        private val logger: Logger = LoggerFactory.getLogger(AsynchronousSocketChannel::class.java)
    }

    @Ignore
    fun connectAndReadDataFromServer() {
        logger.debug("started test")
        val hostname = "chat.freenode.net"
        val port = Integer.parseInt("6665")

        val inetSocketAddress = InetSocketAddress(hostname, port)
        logger.debug("Creating AsynchronousSocketChannel")
        val asynchronousSocketChannel: AsynchronousSocketChannel = AsynchronousSocketChannel.open()
        logger.debug("Created AsynchronousSocketChannel")
        val result = asynchronousSocketChannel.connect(inetSocketAddress)
        logger.debug("Waiting to connect")
        result.get()
        logger.debug("Connected")

        val readByteArray = ByteArray(4096)
        while (asynchronousSocketChannel.isOpen) {
            logger.debug("Reading from socket")

            val readByteBuffer = ByteBuffer.allocate(4096)
            val readResult = asynchronousSocketChannel.read(readByteBuffer)
            val readResultGet = readResult.get()
            logger.debug("readResultGet: {}", readResultGet)
            if(readResultGet == -1) {
                logger.debug("End of stream reached")
                logger.debug("Closing connection")

                asynchronousSocketChannel.close()
                break
            }

            readByteBuffer.flip()
            val readByteBufferLimit = readByteBuffer.limit()
            logger.debug("readByteBufferLimit: {}", readByteBufferLimit)
            readByteBuffer.get(readByteArray, 0, readByteBufferLimit)
            readByteBuffer.clear()
            val readString = readByteArray.toString(Charset.forName("UTF-8"))

            logger.debug("Printing socket output")
            logger.debug(readString)
        }

        logger.debug("Closing connection")
        asynchronousSocketChannel.close()
        logger.debug("Connection closed")
    }
}