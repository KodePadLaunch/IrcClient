package com.kodepad.irc.temp

import kotlin.test.Test

class ParserUnitTest {
    @Test
    fun parseMessageTest() {
        val inputString = "@id=234AB :dan!d@localhost PRIVMSG #chan :Hey what's up!"
        val match = Regex("").find(inputString)!!
    }
}