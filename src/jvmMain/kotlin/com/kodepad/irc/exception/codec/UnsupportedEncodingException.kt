package com.kodepad.irc.exception.codec

class UnsupportedEncodingException: CodecException {
    constructor() : super()
    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)
    constructor(cause: Throwable) : super(cause)
}