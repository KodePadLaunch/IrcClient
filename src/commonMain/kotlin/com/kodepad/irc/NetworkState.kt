package com.kodepad.irc

data class NetworkState(
    var connected: Boolean = false,
    var nickname: String? = null
)
