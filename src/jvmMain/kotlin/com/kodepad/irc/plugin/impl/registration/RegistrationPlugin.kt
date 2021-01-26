package com.kodepad.irc.plugin.impl.registration

import com.kodepad.irc.dto.Message
import com.kodepad.irc.plugin.Plugin
import com.kodepad.irc.plugin.PluginHook
import org.slf4j.LoggerFactory

class RegistrationPlugin(private val pluginHook: PluginHook): Plugin {
    companion object {
        private val logger = LoggerFactory.getLogger(RegistrationPlugin::class.java)
    }

    override fun onInit() {
        val nickMessage = Message(
            null,
            null,
            "NICK",
            listOf(pluginHook.networkState.user.nickname)
        )
        val userMessage = Message(
            null,
            null,
            "USER",
            listOf(pluginHook.networkState.user.username, "0", "*", pluginHook.networkState.user.realname)
        )

        logger.debug("nickMessage: {}", nickMessage)
        logger.debug("userMessage: {}", userMessage)

        pluginHook.sendMessageToServer(nickMessage)
        pluginHook.sendMessageToServer(userMessage)
    }

    override fun onServerMessage(message: Message) {
        logger.debug("onServerMessage called!")
    }
}