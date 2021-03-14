package com.kodepad.irc.parser

// Ast as in Abstract syntax tree
data class Ast(
    val matchedString: String,
    val subProductions: List<Ast>,
    val isMatched: Boolean,
    val token: Token = Token.IGNORE
)
