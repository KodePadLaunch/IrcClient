package com.kodepad.irc

import com.kodepad.irc.codec.CodecFactory
import com.kodepad.irc.codec.Encoding
import com.kodepad.irc.connection.ConnectionImpl
import com.kodepad.irc.event.EventListener
import com.kodepad.irc.handler.CommandHandlerFactory
import com.kodepad.irc.handler.MessageHandler
import com.kodepad.irc.message.Message
import com.kodepad.irc.message.client.sending.Notice
import com.kodepad.irc.message.client.sending.PrivMsg
import com.kodepad.irc.network.Network
import com.kodepad.irc.network.NetworkImpl
import com.kodepad.irc.network.NetworkState
import com.kodepad.irc.serdes.SerDesFactory
import com.kodepad.irc.socket.SocketFactory
import com.kodepad.irc.vo.User
import com.kodepad.irc.logging.LoggerFactory

class IrcClient : Client {
    companion object {
        private val logger = LoggerFactory.getLogger(IrcClient::class)

        const val DELIMITER = "\r\n"
    }

    override fun joinNetwork(
        hostname: String,
        port: Int,
        user: User,
        encoding: Encoding,
        noticeEventListener: EventListener<Notice>?,
        privMsgEventListener: EventListener<PrivMsg>?,
        rawMessageEventListener: EventListener<Message>?,
    ): Network {
        logger.debug("joinNetwork called!")

        val networkState = NetworkState(
            user
        )

        val connection = ConnectionImpl(
            SocketFactory.create(
                hostname,
                port,
                CodecFactory.getCodec(encoding).encode(DELIMITER)
            ),
            CodecFactory.getCodec(encoding),
            SerDesFactory.getSerdes(Message::class),
        )

        val commandHandlerFactory =
            CommandHandlerFactory(connection, networkState, noticeEventListener, privMsgEventListener)

        val messageHandler = MessageHandler(
            commandHandlerFactory,
            rawMessageEventListener,
        )

        return NetworkImpl(
            networkState,
            connection,
            messageHandler
        )
    }
}