package com.kodepad.irc.event.subscriber.impl

import com.kodepad.irc.event.subscriber.SuspendingEventSubscriber
import com.kodepad.irc.socket.raw.RawSocket
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SocketWriteSuspendingEventSubscriber(
    private val rawSocket: RawSocket
): SuspendingEventSubscriber<ByteArray> {

    override suspend fun onEvent(event: ByteArray) {
        withContext(Dispatchers.IO) {
            rawSocket.write(event)
        }
    }
}