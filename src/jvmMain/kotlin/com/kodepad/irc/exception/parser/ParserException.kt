package com.kodepad.irc.exception.parser

import com.kodepad.irc.exception.IrcClientException

open class ParserException: IrcClientException {
    constructor() : super()
    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)
    constructor(cause: Throwable) : super(cause)
}