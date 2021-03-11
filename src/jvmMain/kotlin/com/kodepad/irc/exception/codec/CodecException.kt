package com.kodepad.irc.exception.codec

import com.kodepad.irc.exception.IrcClientException

open class CodecException: IrcClientException {
    constructor() : super()
    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)
    constructor(cause: Throwable) : super(cause)
}