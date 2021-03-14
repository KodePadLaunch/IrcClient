package com.kodepad.irc.parser

// todo: Convert all string handling to UTF-8 as it's common for IRC
object StringConstants {
    const val ALPHA = "[a-zA-Z]"
    const val DIGIT = "[0-9]"
    const val HEXDIGIT = "[0-9A-F]"
    const val SPECIAL = "[" + "\\[" + "\\]" + "\\\\" + "`" + "_" + "\\^" + "\\{" + "\\|" + "\\}" + "]"
    // todo: Check the below two very carefully
    const val NUL = "\u0000"
    const val SPACE = "\u0020"
    const val CR = "\u000D"
    const val LF = "\u000A"
    const val COLON = "\u003A"
    const val SEMICOLON = "\u003B"
    const val BELL = "\u0007"
    const val AT = "@"

    const val NOSPCRLFCL = "[^$NUL$CR$LF$SPACE$COLON]"
    const val NOSPCRLFSCBL = "[^$NUL$BELL$CR$LF$SEMICOLON$SPACE]"
    const val USER = "[^$NUL$CR$LF$SPACE$AT]"
    const val HOST = "[^$NUL$CR$LF$SPACE]"
    const val ANY_CHARACTER = "[a-zA-Z0-9:]"
    const val CRLF = "$CR$LF"
    const val DOT = "."
    const val DASH = "-"
    const val EQUAL = "="
    const val FORWARD_SLASH = "/"

}