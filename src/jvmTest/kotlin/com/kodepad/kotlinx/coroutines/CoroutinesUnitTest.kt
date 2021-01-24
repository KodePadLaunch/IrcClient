package com.kodepad.kotlinx.coroutines

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.slf4j.LoggerFactory
import java.lang.Thread.sleep
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.test.Test
import kotlin.test.assertEquals

class CoroutinesUnitTest {
    companion object {
        private val logger = LoggerFactory.getLogger(CoroutinesUnitTest::class.java)
    }

    @Test
    fun raceConditionTest() {
        logger.info("Starting test!")

        val coroutineScope = CoroutineScope(Dispatchers.Default)

        logger.debug("job1 starting")
        val job1 = coroutineScope.launch {
            sleep(5_000)
            logger.info("job1 print 1")
            logger.info("job1 print 2")
        }

        logger.debug("job2 starting")
        val job2 = coroutineScope.launch {
            logger.info("job2 print 1")
            logger.info("job2 print 2")
        }

        logger.debug("waiting for job1 and job2 to finish")
        runBlocking {
            job1.join()
            job2.join()
        }

        logger.info("Test completed!")
    }
}