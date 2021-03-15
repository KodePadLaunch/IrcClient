package com.kodepad.irc.message.client.sending

import com.kodepad.irc.event.Event
import com.kodepad.irc.exception.message.InvalidServerMessageException
import com.kodepad.irc.message.Message

data class PrivMsg(
    val message: Message,
): Event {
    val targets: List<String>
    val text: String

    init {
        val parameters = message.parameters?:throw InvalidServerMessageException("Null parameters")
        if(parameters.size == 2) {
            targets = parameters[0].split(",")
            text = parameters[1]
        }
        else {
            throw InvalidServerMessageException("2 parameters expected")
        }
    }
}
