package com.kodepad.irc.network

import com.kodepad.irc.channel.Channel
import com.kodepad.irc.vo.User
import java.io.Closeable

interface Network: Closeable {
    fun getUser(): User
    fun joinChannel(name: String): Channel
}