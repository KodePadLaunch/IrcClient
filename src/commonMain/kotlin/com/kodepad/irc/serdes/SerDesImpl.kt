package com.kodepad.irc.serdes

import com.kodepad.irc.Message
import com.kodepad.irc.exception.serdes.TokenNotFoundException
import com.kodepad.irc.logging.LoggerFactory
import com.kodepad.irc.parser.Ast
import com.kodepad.irc.parser.Parser
import com.kodepad.irc.parser.Token

class SerDesImpl(private val messageParser: Parser): SerDes<Message> {
    companion object {
        private val logger = LoggerFactory.getLogger(SerDesImpl::class)
    }

    override fun serialize(input: Message): String {
        logger.debug("input: {}", input)
        val messageString = StringBuilder()

        val tagsString = toStringTags(input.tags)
        val commandString = input.command
        val sourceString = toStringSource(input.source)
        val paramatersString = toStringParameters(input.parameters)

        if(tagsString != null) {
            messageString.append("$tagsString ")
        }
        if(sourceString != null) {
            messageString.append("$sourceString ")
        }

        messageString.append(commandString)

        if(paramatersString != null) {
            messageString.append(" $paramatersString")
        }

        messageString.append("\r\n")

        logger.debug("messageString: {}", messageString)
        return messageString.toString()
    }

    override fun deserialize(input: String): Message {
        logger.debug("input: {}", input)

        val ast = messageParser.parse(input)

        val tags = getTags(ast)
        val source = getSource(ast)
        val command = getCommand(ast)
        val parameters = getParameters(ast)
        val message = Message(tags, source, command, parameters)

        logger.debug("message: {}", message)
        return message
    }

    private fun toStringTags(tags: Map<String, String?>?): String? {
        val tagStringList = tags?.map { tag -> "${tag.key}${tag.value.let { value -> "=$value" }}" }
        val tagsString = tagStringList?.joinToString(";")

        return if(tagsString != null) {
            "@$tagsString"
        }
        else {
            null
        }
    }

    private fun toStringSource(source: String?): String? {
        return if(source != null) {
            ":$source"
        }
        else {
            null
        }
    }

    private fun toStringParameters(parameters: List<String>?): String? {
        return if(parameters != null) {
            val lastParameter = parameters.last()
            val parametersExcludingLast = parameters.dropLast(1)
            val parametersExcludingLastString = parametersExcludingLast.joinToString(" ")

            if(lastParameter.contains(" ")) {
                "$parametersExcludingLastString :$lastParameter"
            }
            else {
                "$parametersExcludingLastString $lastParameter"
            }
        }
        else {
            null
        }
    }

    private fun getTags(ast: Ast): Map<String, String?>? {
        return try {
            val tagsAst = findToken(ast, Token.Tags)
            val tagAsts = findAllTokens(tagsAst, Token.Tag)

            tagAsts.associate { tagAst ->
                val key = findToken(tagAst, Token.Key).matchedString
                val value = findToken(tagAst, Token.Value).matchedString
                Pair(key, value)
            }
        } catch (exception: TokenNotFoundException) {
            null
        }
    }

    private fun findToken(ast: Ast, token: Token): Ast {
        val deque: ArrayDeque<Ast> = ArrayDeque()
        deque.addLast(ast)
        while (!deque.isEmpty()) {
            val topAst = deque.removeFirst()
            if(topAst.token == token) {
                return topAst
            }
            else {
                for(subAst in topAst.subProductions) {
                    deque.addLast(subAst)
                }
            }
        }

        throw TokenNotFoundException("Token $token not found")
    }

    private fun findAllTokens(ast: Ast, token: Token): List<Ast> {
        val matchedAsts = ArrayList<Ast>()

        val deque: ArrayDeque<Ast> = ArrayDeque()
        deque.addLast(ast)
        while (!deque.isEmpty()) {
            val topAst = deque.removeFirst()
            // Assuming same tags are not nested
            if(topAst.token == token) {
                matchedAsts.add(topAst)
            }
            else {
                for(subAst in topAst.subProductions) {
                    deque.addLast(subAst)
                }
            }
        }

        return matchedAsts
    }

    private fun getSource(ast: Ast): String? {
        return try {
            val prefixAst = findToken(ast, Token.Prefix)
            prefixAst.matchedString
        } catch (exception: TokenNotFoundException) {
            null
        }
    }

    private fun getCommand(ast: Ast): String {
        val commandAst = findToken(ast, Token.Command)
        return commandAst.matchedString
    }

    private fun getParameters(ast: Ast): List<String>? {
        return try {
            val paramsAst = findToken(ast, Token.Params)
            val middleAsts = findAllTokens(paramsAst, Token.Middle)
            val trailingAst = findToken(paramsAst, Token.Trailing)

            middleAsts.map { middleAst ->
                middleAst.matchedString
            } + trailingAst.matchedString
        } catch (exception: TokenNotFoundException) {
            null
        }
    }
}