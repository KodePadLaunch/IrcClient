package com.kodepad.irc.event

import kotlin.reflect.KClass

class EventDispatcherImpl: EventDispatcher {
    private val eventListenerMap = HashMap<KClass<Event>, ArrayList<EventListener<Event>>>()

    override fun <T : Event> addListener(kClass: KClass<T>, eventListener: EventListener<T>) {
        // todo: Put this inside try and catch and throw appropriate exception
        val key = kClass as KClass<Event>
        val value = eventListener as EventListener<Event>

        if(!eventListenerMap.containsKey(key)) {
            eventListenerMap[key] = ArrayList()
        }

        eventListenerMap[key]?.add(value)
    }

    override fun <T : Event> dispatch(kClass: KClass<T>, event: T) {
        val key = kClass as KClass<Event>

        eventListenerMap[key]?.forEach { eventListener ->
            eventListener.onEvent(event)
        }
    }
}