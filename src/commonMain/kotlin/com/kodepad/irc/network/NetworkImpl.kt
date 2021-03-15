package com.kodepad.irc.network

import com.kodepad.irc.connection.Connection
import com.kodepad.irc.event.Event
import com.kodepad.irc.event.EventDispatcher
import com.kodepad.irc.event.EventListener
import com.kodepad.irc.handler.Handler
import com.kodepad.irc.Message
import com.kodepad.irc.NetworkState
import kotlin.coroutines.EmptyCoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield
import com.kodepad.irc.logging.LoggerFactory
import com.kodepad.irc.command.JoinCommand
import com.kodepad.irc.command.NickCommand
import com.kodepad.irc.command.PrivMsgCommand
import com.kodepad.irc.command.UserCommand
import kotlin.reflect.KClass

// todo: Test the shutdown logic
// todo: This class is doing too much break it down
class NetworkImpl(
    private val networkState: NetworkState,
    private val connection: Connection,
    private val messageHandler: Handler,
    private val eventDispatcher: EventDispatcher,
    private val coroutineScope: CoroutineScope = CoroutineScope(EmptyCoroutineContext)
): Network {
    companion object {
        private val logger = LoggerFactory.getLogger(NetworkImpl::class)
    }

    override fun <T : Event> addEventListener(kClass: KClass<T>, eventListener: EventListener<T>) {
        eventDispatcher.addListener(kClass, eventListener)
    }

    override suspend fun connectAndRegister(nickCommand: NickCommand, userCommand: UserCommand) {
        connection.connect()
        register(nickCommand, userCommand)

        coroutineScope.launch {
            logger.debug("connection read coroutine called!")
            while (true) {
                yield()
                val message = connection.read()
                logger.debug("message: $message")
                messageHandler.onMessage(message)
            }
        }

        networkState.connected = true
    }

    override fun getNetworkState() = networkState

    override suspend fun joinChannel(joinCommand: JoinCommand) {
        logger.debug("joinChannel called!")

        connection.write(joinCommand.getMessage())
    }

    override suspend fun sendPrivMsg(privMsgCommand: PrivMsgCommand) {
        logger.debug("sendMessage called!")

        connection.write(privMsgCommand.getMessage())
    }

    override suspend fun sendRawMessage(message: Message) {
        connection.write(message)
    }

    override suspend fun close() {
        logger.debug("close called!")

        coroutineScope.cancel()
        connection.close()
    }

    private suspend fun register(nickCommand: NickCommand, userCommand: UserCommand) {
        logger.debug("register called!")

        logger.debug("nickMessage: {}", nickCommand)
        logger.debug("userMessage: {}", userCommand)

        connection.write(nickCommand.getMessage())
        networkState.nickname = nickCommand.nickname

        connection.write(userCommand.getMessage())
    }
}