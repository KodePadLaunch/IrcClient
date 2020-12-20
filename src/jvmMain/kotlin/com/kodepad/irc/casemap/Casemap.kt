package com.kodepad.irc.casemap

interface Casemap {
    fun toUpper(string: String): String
    fun toLower(string: String): String
    fun isEqualCaseSensitive(string1: String, string2: String): Boolean
    fun isEqualCaseInsensitive(string1: String, string2: String): Boolean
}