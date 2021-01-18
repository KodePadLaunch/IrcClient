package com.kodepad.java.nio

import com.kodepad.irc.parser.StringConstants.CR
import com.kodepad.irc.parser.StringConstants.LF
import com.kodepad.irc.parser.StringConstants.NUL
import com.kodepad.irc.parser.StringConstants.SEMICOLON
import com.kodepad.irc.parser.StringConstants.SPACE
import kotlin.test.Test

class SelectorTest {

    @Test
    fun matchEscapedValueRegex() {
        val result = "[^$NUL$CR$LF$SEMICOLON$SPACE]*".toRegex().find("hello, world!")
        println(result)
        println(result?.value)
        println(result?.groupValues)
        println(result?.range)
        println(result?.destructured)
    }
}