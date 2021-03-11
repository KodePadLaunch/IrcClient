package com.kodepad.irc.logging

import com.kodepad.irc.exception.logger.GetMarkerException
import org.slf4j.MarkerFactory

object Markers {
    val TOP_LEVEL_PARSER = MarkerFactory.getMarker("TOP_LEVEL_PARSER")?: throw GetMarkerException("getMarker failed for MESSAGE_FLOW")
    val MESSAGE_FLOW = MarkerFactory.getMarker("MESSAGE_FLOW")?: throw GetMarkerException("getMarker failed for MESSAGE_FLOW")
    val TEST_FLOW = MarkerFactory.getMarker("TEST_FLOW")?: throw GetMarkerException("getMarker failed for TEST_FLOW")
}
