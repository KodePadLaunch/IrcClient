package com.kodepad.irc.parser

import kotlin.test.Test

class StringConstantsUnitTest {
    private fun addStartAndEndDemarcations(char: Char): String {
        return "^$char$"
    }

    // todo: Add logger
    @Test
    fun printCharacters() {
//        println("SEMICOLON : " + addStartAndEndDemarcations(StringConstants.SEMICOLON))
//        println("CR        : " + addStartAndEndDemarcations(StringConstants.CR))
//        println("LF        : " + addStartAndEndDemarcations(StringConstants.LF))
//        println("NUL       : " + addStartAndEndDemarcations(StringConstants.NUL))
    }
}