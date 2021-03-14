package com.kodepad.irc.parser

enum class Token {
    IGNORE,

    // Non terminals
    Command,
    EscapedValue,
    Host,
    Key,
    Letter,
    Message,
    Middle,
    Params,
    Prefix,
    Space,
    Tag,
    Tags,
    Trailing,
    Vendor,
    Value,
}
