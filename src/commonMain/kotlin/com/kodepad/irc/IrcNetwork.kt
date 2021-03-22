package com.kodepad.irc

import com.kodepad.irc.codec.Encoding
import com.kodepad.irc.command.JoinCommand
import com.kodepad.irc.command.NickCommand
import com.kodepad.irc.command.PrivMsgCommand
import com.kodepad.irc.command.UserCommand
import com.kodepad.irc.connection.ConnectionFactory
import com.kodepad.irc.event.Event
import com.kodepad.irc.event.EventDispatcherImpl
import com.kodepad.irc.event.EventListener
import com.kodepad.irc.handler.CommandHandlerFactory
import com.kodepad.irc.handler.MessageHandler
import com.kodepad.irc.logging.LoggerFactory
import com.kodepad.irc.network.Network
import com.kodepad.irc.network.NetworkImpl
import com.kodepad.irc.state.MutableNetworkState
import kotlin.reflect.KClass

class IrcNetwork(
    hostname: String,
    port: Int,
    encoding: Encoding = Encoding.UTF_8,
) : Network {
    companion object {
        private val logger = LoggerFactory.getLogger(IrcNetwork::class)

        const val DELIMITER = "\r\n"
    }
    // todo: Properly form network states
    private val mutableNetworkState = MutableNetworkState()
    private val eventDispatcher = EventDispatcherImpl()
    private val connection = ConnectionFactory.create(
        hostname,
        port,
        encoding,
        eventDispatcher,
        mutableNetworkState
    )
    private val commandHandlerFactory = CommandHandlerFactory(
        connection,
        mutableNetworkState,
        eventDispatcher,
    )
    private val messageHandler = MessageHandler(
        commandHandlerFactory,
        eventDispatcher
    )
    private val networkImpl = NetworkImpl(
        mutableNetworkState,
        connection,
        messageHandler,
        eventDispatcher
    )

    override fun <T : Event> registerEventListener(kClass: KClass<T>, eventListener: EventListener<T>) = networkImpl.registerEventListener(kClass, eventListener)
    override suspend fun connectAndRegister(nickCommand: NickCommand, userCommand: UserCommand) = networkImpl.connectAndRegister(nickCommand, userCommand)
    override fun getNetworkState() = networkImpl.getNetworkState()
    override suspend fun joinChannel(joinCommand: JoinCommand) = networkImpl.joinChannel(joinCommand)
    override suspend fun sendPrivMsg(privMsgCommand: PrivMsgCommand) = networkImpl.sendPrivMsg(privMsgCommand)
    override suspend fun sendRawMessage(message: Message) = networkImpl.sendRawMessage(message)
    override suspend fun close() = networkImpl.close()
}