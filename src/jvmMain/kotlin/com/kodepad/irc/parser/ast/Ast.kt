package com.kodepad.irc.parser.ast

// Ast as in Abstract syntax tree
data class Ast(
    val matchedString: String,
    val subProductions: List<Ast>,
    val isMatched: Boolean,
)
