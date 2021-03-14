package com.kodepad.irc.logging

class Slf4jToIrcLoggerAdapter(
    private val logger: org.slf4j.Logger
    ): Logger {
    override fun trace(msg: String) {
        logger.trace(msg)
    }

    override fun trace(format: String, arg: Any) {
        logger.trace(format, arg)
    }

    override fun debug(msg: String) {
        logger.debug(msg)
    }

    override fun debug(format: String, arg: Any) {
        logger.debug(format, arg)
    }

    override fun debug(format: String, arg1: Any, arg2: Any) {
        logger.debug(format, arg1, arg2)
    }

    override fun debug(format: String, vararg arguments: Any) {
        logger.debug(format, arguments)
    }

    override fun debug(marker: Marker, msg: String) {
        val slf4jToIrcMarkerAdapter = marker as Slf4jToIrcMarkerAdapter
        logger.debug(slf4jToIrcMarkerAdapter.marker, msg)
    }

    override fun debug(marker: Marker, format: String, arg: Any) {
        val slf4jToIrcMarkerAdapter = marker as Slf4jToIrcMarkerAdapter
        logger.debug(slf4jToIrcMarkerAdapter.marker, format, arg)
    }

    override fun info(msg: String) {
        logger.info(msg)
    }

    override fun info(format: String, arg: Any) {
        logger.info(format, arg)
    }

    override fun warn(msg: String) {
        logger.warn(msg)
    }

    override fun warn(format: String, arg: Any) {
        logger.warn(format, arg)
    }

    override fun error(msg: String) {
        logger.error(msg)
    }

    override fun error(format: String, arg: Any) {
        logger.error(format, arg)
    }
}