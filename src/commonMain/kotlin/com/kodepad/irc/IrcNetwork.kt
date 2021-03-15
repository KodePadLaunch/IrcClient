package com.kodepad.irc

import com.kodepad.irc.codec.CodecFactory
import com.kodepad.irc.codec.Encoding
import com.kodepad.irc.connection.ConnectionImpl
import com.kodepad.irc.event.Event
import com.kodepad.irc.event.EventDispatcherImpl
import com.kodepad.irc.event.EventListener
import com.kodepad.irc.handler.CommandHandlerFactory
import com.kodepad.irc.handler.MessageHandler
import com.kodepad.irc.logging.LoggerFactory
import com.kodepad.irc.message.Message
import com.kodepad.irc.network.Network
import com.kodepad.irc.network.NetworkImpl
import com.kodepad.irc.network.NetworkState
import com.kodepad.irc.serdes.SerDesFactory
import com.kodepad.irc.socket.SocketFactory
import com.kodepad.irc.vo.User
import kotlin.reflect.KClass

class IrcNetwork(
    hostname: String,
    port: Int,
    user: User,
    encoding: Encoding = Encoding.UTF_8,
) : Network {
    companion object {
        private val logger = LoggerFactory.getLogger(IrcNetwork::class)

        const val DELIMITER = "\r\n"
    }
    // todo: Properly form network states
    private val networkState = NetworkState(
        user
    )
    private val connection = ConnectionImpl(
        SocketFactory.create(
            hostname,
            port,
            CodecFactory.getCodec(encoding).encode(DELIMITER)
        ),
        CodecFactory.getCodec(encoding),
        SerDesFactory.getSerdes(Message::class),
    )
    private val eventDispatcher = EventDispatcherImpl()
    private val commandHandlerFactory = CommandHandlerFactory(
        connection,
        networkState,
        eventDispatcher,
    )
    private val messageHandler = MessageHandler(
        commandHandlerFactory,
        eventDispatcher
    )

    private val networkImpl = NetworkImpl(
        networkState,
        connection,
        messageHandler,
        eventDispatcher
    )

    override fun <T : Event> addEventListener(kClass: KClass<T>, eventListener: EventListener<T>) = networkImpl.addEventListener(kClass, eventListener)
    override suspend fun connectAndRegister() = networkImpl.connectAndRegister()
    override fun getNetworkState(): NetworkState = networkImpl.getNetworkState()
    override suspend fun joinChannel(name: String) = networkImpl.joinChannel(name)
    override suspend fun sendMessage(target: String, message: String) = networkImpl.sendMessage(target, message)
    override suspend fun sendRawMessage(message: Message) = networkImpl.sendRawMessage(message)
    override suspend fun close() = networkImpl.close()
}