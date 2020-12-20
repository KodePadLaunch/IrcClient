package com.kodepad.irc.parser

import com.kodepad.irc.parser.ast.Ast

interface Parser {
    fun parse(input: String): Ast
}