package com.kodepad.irc.logging

expect object MarkerFactory {
    fun getMarker(name: String): Marker
}