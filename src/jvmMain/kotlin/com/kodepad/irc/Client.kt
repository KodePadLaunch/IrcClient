package com.kodepad.irc

import com.kodepad.irc.codec.Encoding
import com.kodepad.irc.event.ConnectEventListener
import com.kodepad.irc.event.MessageEventListener
import com.kodepad.irc.network.Network
import com.kodepad.irc.vo.User

internal interface Client {
    fun joinNetwork(
        hostname: String,
        port: Int,
        user: User,
        encoding: Encoding = Encoding.UTF_8,
        messageEventListener: MessageEventListener? = null,
        connectEventListener: ConnectEventListener? = null,
    ): Network
}