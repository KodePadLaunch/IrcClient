package com.kodepad.irc.state

import kotlinx.coroutines.flow.MutableStateFlow

class MutableNetworkState: NetworkState {
    override val networkLifecycle = MutableStateFlow(NetworkLifecycle.READY)
    override val nickname: MutableStateFlow<String?> = MutableStateFlow(null)
}
