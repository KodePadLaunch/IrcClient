package com.kodepad.irc.plugin.impl.registration

import com.kodepad.irc.plugin.Plugin
import com.kodepad.irc.plugin.PluginFactory
import com.kodepad.irc.plugin.PluginHook

object RegistrationPluginFactory: PluginFactory {
    override fun create(pluginHook: PluginHook): Plugin  = RegistrationPlugin(pluginHook)
}