package com.kodepad.irc.network

import com.kodepad.irc.connection.Connection
import com.kodepad.irc.handler.Handler
import com.kodepad.irc.message.Message
import kotlin.coroutines.EmptyCoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.yield
import org.slf4j.LoggerFactory

// todo: Test the shutdown logic
// todo: This class is doing too much break it down
class NetworkImpl(
    private val networkState: NetworkState,
    private val connection: Connection,
    private val messageHandler: Handler,
    private val coroutineScope: CoroutineScope = CoroutineScope(EmptyCoroutineContext)
): Network {
    companion object {
        private val logger = LoggerFactory.getLogger(NetworkImpl::class.java)
    }

    // todo: Check if the EmptyCorutineContext usage is correct here

    init {
        runBlocking {
            connection.connect()
            register()
        }

        coroutineScope.launch {
            logger.debug("connection read coroutine called!")
            while (true) {
                yield()
                val message = connection.read()
                logger.debug("message: $message")
                messageHandler.onMessage(message)
            }
        }
    }

    override fun getNetworkState() = networkState

    override suspend fun joinChannel(name: String) {
        logger.debug("joinChannel called!")

        val joinMessage = Message(
                null,
                null,
                "JOIN",
                listOf(name)
        )

        connection.write(joinMessage)
    }

    override suspend fun sendMessage(target: String, message: String) {
        logger.debug("sendMessage called!")

        val privmsg = Message(
                null,
                null,
                "PRIVMSG",
                listOf(target, message)
        )

        connection.write(privmsg)
    }


    override fun close() {
        logger.debug("close called!")

        coroutineScope.cancel()
        connection.close()
    }

    private suspend fun register() {
        logger.debug("register called!")

        val nickMessage = Message(
                null,
                null,
                "NICK",
                listOf(networkState.user.nickname)
        )
        val userMessage = Message(
                null,
                null,
                "USER",
                listOf(networkState.user.username, "0", "*", networkState.user.realname)
        )

        logger.debug("nickMessage: {}", nickMessage)
        logger.debug("userMessage: {}", userMessage)

        connection.write(nickMessage)
        connection.write(userMessage)
    }
}