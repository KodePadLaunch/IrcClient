package com.kodepad.kotlin

import kotlin.test.Test
import kotlin.test.assertEquals

class CharUnitTest {
    @Test
    fun testCodePointConversion() {
        assertEquals(0x003a, ':'.toInt())
        assertEquals(0x000d, '\r'.toInt())
        assertEquals(0x000a, '\n'.toInt())
        assertEquals(0x003b, ';'.toInt())
        assertEquals(0x0020, ' '.toInt())
    }
}