package com.kodepad.irc.parser

interface Parser {
    fun parse(input: String): Ast
}