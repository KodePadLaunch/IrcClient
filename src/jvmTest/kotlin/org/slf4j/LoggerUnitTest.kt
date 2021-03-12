package org.slf4j

import kotlin.test.Test

class LoggerUnitTest {
    private val logger: Logger = LoggerFactory.getLogger(LoggerUnitTest::class.java)

    @Test
    fun traceLog() {
        logger.trace("this is a trace log!")
    }

    @Test
    fun debugLog() {
        logger.debug("this is a debug log")
    }

    @Test
    fun infoLog() {
        logger.info("this is an info log")
    }

    @Test
    fun warnLog() {
        logger.warn("this is a warn log")
    }

    @Test
    fun errorLog() {
        logger.error("this is an error log")
    }
}