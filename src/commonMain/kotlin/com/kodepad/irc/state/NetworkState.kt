package com.kodepad.irc.state

import kotlinx.coroutines.flow.StateFlow

interface NetworkState {
    val networkLifecycle: StateFlow<NetworkLifecycle>
    val nickname: StateFlow<String?>
}
