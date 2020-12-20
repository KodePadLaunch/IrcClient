package com.kodepad.irc.parser

// todo: Convert all string handling to UTF-8 as it's common for IRC
object UnicodeCodePoints {
    const val COLON = 0x003a
    const val CR = 0x000d
    const val LF = 0x000a
    const val NUL = 0x0000
    const val SEMICOLON = 0x003b
    const val SPACE = 0x0020
}