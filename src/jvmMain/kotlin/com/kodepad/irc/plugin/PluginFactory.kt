package com.kodepad.irc.plugin

import com.kodepad.irc.plugin.Plugin
import com.kodepad.irc.plugin.PluginHook

interface PluginFactory {
    fun create(pluginHook: PluginHook): Plugin
}