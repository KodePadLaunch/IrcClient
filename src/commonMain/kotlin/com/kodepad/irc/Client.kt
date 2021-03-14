package com.kodepad.irc

import com.kodepad.irc.codec.Encoding
import com.kodepad.irc.event.EventListener
import com.kodepad.irc.message.Message
import com.kodepad.irc.message.client.sending.Notice
import com.kodepad.irc.message.client.sending.PrivMsg
import com.kodepad.irc.network.Network
import com.kodepad.irc.vo.User

internal interface Client {
    fun joinNetwork(
        hostname: String,
        port: Int,
        user: User,
        encoding: Encoding = Encoding.UTF_8,
        noticeEventListener: EventListener<Notice>? = null,
        privMsgEventListener: EventListener<PrivMsg>? = null,
        rawMessageEventListener: EventListener<Message>? = null,
    ): Network
}