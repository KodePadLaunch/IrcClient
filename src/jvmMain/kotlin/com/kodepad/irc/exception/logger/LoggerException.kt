package com.kodepad.irc.exception.logger

import com.kodepad.irc.exception.IrcClientException

open class LoggerException: IrcClientException {
    constructor() : super()
    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)
    constructor(cause: Throwable) : super(cause)
}