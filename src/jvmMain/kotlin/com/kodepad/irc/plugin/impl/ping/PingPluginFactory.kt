package com.kodepad.irc.plugin.impl.ping

import com.kodepad.irc.plugin.Plugin
import com.kodepad.irc.plugin.PluginFactory
import com.kodepad.irc.plugin.PluginHook

object PingPluginFactory: PluginFactory {
    override fun create(pluginHook: PluginHook): Plugin  = PingPlugin(pluginHook)
}