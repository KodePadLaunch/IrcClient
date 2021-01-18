package com.kodepad.irc.serdes.deserializer.impl.ast.exception

class TokenNotFoundException: Exception {
    constructor() : super()
    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)
    constructor(cause: Throwable) : super(cause)
}