package com.kodepad.irc.network

import com.kodepad.irc.Message
import com.kodepad.irc.command.JoinCommand
import com.kodepad.irc.command.NickCommand
import com.kodepad.irc.command.PrivMsgCommand
import com.kodepad.irc.command.UserCommand
import com.kodepad.irc.connection.Connection
import com.kodepad.irc.event.Event
import com.kodepad.irc.event.EventDispatcher
import com.kodepad.irc.event.EventListener
import com.kodepad.irc.handler.Handler
import com.kodepad.irc.logging.LoggerFactory
import com.kodepad.irc.state.MutableNetworkState
import com.kodepad.irc.state.NetworkLifecycle
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.reflect.KClass
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield

// todo: Test the shutdown logic
// todo: This class is doing too much break it down
class NetworkImpl(
    private val mutableNetworkState: MutableNetworkState,
    private val connection: Connection,
    private val messageHandler: Handler,
    private val eventDispatcher: EventDispatcher,
    private val coroutineScope: CoroutineScope = CoroutineScope(EmptyCoroutineContext)
): Network {
    companion object {
        private val logger = LoggerFactory.getLogger(NetworkImpl::class)
    }

    override fun <T : Event> registerEventListener(kClass: KClass<T>, eventListener: EventListener<T>) {
        eventDispatcher.addListener(kClass, eventListener)
    }

    // todo: Handle and update CLOSED network lifecycle
    override suspend fun connectAndRegister(nickCommand: NickCommand, userCommand: UserCommand) {
        connection.connect()
        mutableNetworkState.networkLifecycle.value = NetworkLifecycle.CONNECTED
        register(nickCommand, userCommand)
        mutableNetworkState.networkLifecycle.value = NetworkLifecycle.REGISTERED

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

    override fun getNetworkState() = mutableNetworkState

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

        mutableNetworkState.networkLifecycle.value = NetworkLifecycle.CLOSED
    }

    private suspend fun register(nickCommand: NickCommand, userCommand: UserCommand) {
        logger.debug("register called!")

        logger.debug("nickMessage: {}", nickCommand)
        logger.debug("userMessage: {}", userCommand)

        connection.write(nickCommand.getMessage())
        mutableNetworkState.nickname.value = nickCommand.nickname

        connection.write(userCommand.getMessage())
    }
}