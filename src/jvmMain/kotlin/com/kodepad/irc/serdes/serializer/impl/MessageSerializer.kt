package com.kodepad.irc.serdes.serializer.impl

import com.kodepad.irc.serdes.serializer.Serializer
import com.kodepad.irc.dto.Message
import org.slf4j.LoggerFactory

class MessageSerializer: Serializer<Message> {
    companion object {
        private val logger = LoggerFactory.getLogger(MessageSerializer::class.java)
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
}