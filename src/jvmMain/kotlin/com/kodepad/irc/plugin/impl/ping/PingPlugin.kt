package com.kodepad.irc.plugin.impl.ping

import com.kodepad.irc.dto.Message
import com.kodepad.irc.plugin.Plugin
import com.kodepad.irc.plugin.PluginHook
import com.kodepad.irc.plugin.UnsupportedCommandException
import org.slf4j.LoggerFactory

class PingPlugin(private val pluginHook: PluginHook): Plugin {
    companion object {
        private val logger = LoggerFactory.getLogger(PingPlugin::class.java)
    }

    override fun registeredCommands(): List<String> = listOf("PING")

    override fun onInit() {
        logger.debug("init called!")
    }

    override fun onServerMessage(message: Message) {
        logger.debug("message: {}", message)
        val pongMessage = when(message.command) {
            "PING" -> Message(
                null,
                null,
                "PONG",
                listOf(
                    pluginHook.networkState.user.nickname,
                    message.parameters?.get(0)?:throw TargetServerMissingException("PING target server argument can't be null")
                )
            )
            else -> throw UnsupportedCommandException("Given command is not supported")
        }
    }
}