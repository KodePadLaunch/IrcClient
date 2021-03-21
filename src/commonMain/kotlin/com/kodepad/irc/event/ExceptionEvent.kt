package com.kodepad.irc.event

import com.kodepad.irc.exception.IrcClientException

class ExceptionEvent: IrcClientException, Event {
    constructor() : super()
    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)
    constructor(cause: Throwable) : super(cause)
}