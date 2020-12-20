package com.kodepad.irc.parser

enum class Token {
    // Non terminals
    Command,
    EscapedValue,
    Host,
    Key,
    Letter,
    Message,
    Params,
    Prefix,
    Space,
    Tag,
    Tags,
    Vendor,

    // Terminals
    // Exact
    MessageAtSignExact,
    MessageColonSignExact,

    // Regex
    Digit0To9Regex,
    HostAnyCharacterRegex,

}
