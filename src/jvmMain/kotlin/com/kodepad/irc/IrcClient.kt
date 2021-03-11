package com.kodepad.irc

import com.kodepad.irc.codec.CodecFactory
import com.kodepad.irc.codec.Encoding
import com.kodepad.irc.connection.ConnectionImpl
import com.kodepad.irc.event.ConnectEventListener
import com.kodepad.irc.event.MessageEventListener
import com.kodepad.irc.handler.CommandHandlerFactory
import com.kodepad.irc.handler.MessageHandler
import com.kodepad.irc.message.Message
import com.kodepad.irc.network.Network
import com.kodepad.irc.network.NetworkImpl
import com.kodepad.irc.network.NetworkState
import com.kodepad.irc.serdes.SerDesFactory
import com.kodepad.irc.socket.nio.JavaNioSocketFactoryImpl
import com.kodepad.irc.vo.User
import org.slf4j.LoggerFactory

class IrcClient : Client {
    companion object {
        private val logger = LoggerFactory.getLogger(IrcClient::class.java)

        const val DELIMITER = "\r\n"
    }

    override fun joinNetwork(
        hostname: String,
        port: Int,
        user: User,
        encoding: Encoding,
        messageEventListener: MessageEventListener?,
        connectEventListener: ConnectEventListener?
    ): Network {
        logger.debug("joinNetwork called!")

        val networkState = NetworkState(
                user
        )

        val connection = ConnectionImpl(
                JavaNioSocketFactoryImpl.create(
                        hostname,
                        port,
                        CodecFactory.getCodec(encoding).encode(DELIMITER)
                ),
                CodecFactory.getCodec(encoding),
                SerDesFactory.getSerdes(Message::class),
        )

        val commandHandlerFactory = CommandHandlerFactory(connection, networkState)

        val messageHandler = MessageHandler(
                messageEventListener,
                commandHandlerFactory
        )

        return NetworkImpl(
                networkState,
                connection,
                messageHandler
        )
    }
}