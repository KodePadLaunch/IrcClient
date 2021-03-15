package com.kodepad.irc.event

import com.kodepad.irc.exception.message.InvalidServerMessageException
import com.kodepad.irc.Message

class NoticeEvent(
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

    override fun toString(): String {
        return "Notice(message=$message, targets=$targets, text='$text')"
    }
}
