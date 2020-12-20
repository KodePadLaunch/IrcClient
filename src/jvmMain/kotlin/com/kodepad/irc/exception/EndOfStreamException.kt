package com.kodepad.irc.exception

import java.lang.Exception

class EndOfStreamException: Exception {
    constructor() : super()
    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)
    constructor(cause: Throwable) : super(cause)
}