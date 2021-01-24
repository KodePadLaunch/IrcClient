package com.kodepad.irc.event.bus.impl

import com.kodepad.irc.event.bus.EventBus
import com.kodepad.irc.event.subscriber.SuspendingEventSubscriber
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import org.slf4j.LoggerFactory

// todo: Pass and create proper coroutine scope hierarchy
class FlowBasedEventBus<T>(subscriberSuspending: SuspendingEventSubscriber<T>): EventBus<T> {
    companion object {
        private val logger = LoggerFactory.getLogger(FlowBasedEventBus::class.java)
    }

    private val eventsFlow = MutableSharedFlow<T>()
    private val readOnlyEventsFlow = eventsFlow.asSharedFlow()

    private val putCoroutineContext = newSingleThreadContext("FlowBasedEventBusThreadContext")
    private val putCoroutineScope = CoroutineScope(putCoroutineContext)

    private val subscriberCoroutineScope = CoroutineScope(Dispatchers.Default)

    init {
        subscriberCoroutineScope.launch {
            readOnlyEventsFlow.collect { event ->
                logger.debug("readOnlyEvent: {}", event)
                subscriberSuspending.onEvent(event)
            }
        }
    }

    override fun put(event: T) {
        logger.debug("event: {}", event)
        putCoroutineScope.launch {
            eventsFlow.emit(event)
        }
    }
}