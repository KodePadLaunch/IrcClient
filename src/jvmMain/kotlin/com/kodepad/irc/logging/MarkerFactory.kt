package com.kodepad.irc.logging

import org.slf4j.MarkerFactory

actual object MarkerFactory {
    actual fun getMarker(name: String): Marker {
        return Slf4jToIrcMarkerAdapter(MarkerFactory.getMarker(name))
    }
}