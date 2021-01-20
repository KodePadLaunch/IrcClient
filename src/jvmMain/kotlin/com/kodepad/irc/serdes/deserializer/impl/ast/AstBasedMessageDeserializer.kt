package com.kodepad.irc.serdes.deserializer.impl.ast

import com.kodepad.irc.dto.Message
import com.kodepad.irc.parser.Parser
import com.kodepad.irc.parser.Token
import com.kodepad.irc.parser.ast.Ast
import com.kodepad.irc.serdes.deserializer.Deserializer
import com.kodepad.irc.serdes.deserializer.impl.ast.exception.TokenNotFoundException
import org.slf4j.LoggerFactory
import java.util.*

class AstBasedMessageDeserializer(private val messageParser: Parser): Deserializer<Message> {
    companion object {
        private val logger = LoggerFactory.getLogger(AstBasedMessageDeserializer::class.java)
    }

    override fun deserialize(input: String): Message {
        logger.debug("input: {}", input)

        val ast = messageParser.parse(input)

        val tags = getTags(ast)
        val source = getSource(ast)
        val command = getCommand(ast)
        val parameters = getParameters(ast)

        return Message(tags, source, command, parameters)
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
        val stack: Stack<Ast> = Stack()
        stack.push(ast)
        while (!stack.isEmpty()) {
            val topAst = stack.pop()
            if(topAst.token == token) {
                return topAst
            }
            else {
                for(subAst in topAst.subProductions) {
                    stack.push(subAst)
                }
            }
        }

        throw TokenNotFoundException("Token $token not found")
    }

    private fun findAllTokens(ast: Ast, token: Token): List<Ast> {
        val matchedAsts = ArrayList<Ast>()

        val stack: Stack<Ast> = Stack()
        stack.push(ast)
        while (!stack.isEmpty()) {
            val topAst = stack.pop()
            // Assuming same tags are not nested
            if(topAst.token == token) {
                matchedAsts.add(topAst)
            }
            else {
                for(subAst in topAst.subProductions) {
                    stack.push(subAst)
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