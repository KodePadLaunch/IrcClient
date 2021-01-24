package com.kodepad.kotlinx.coroutines.flow

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.slf4j.LoggerFactory
import java.lang.Thread.sleep
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.test.Test
import kotlin.test.assertEquals

class SharedFlowUnitTest {
    companion object {
        private val logger = LoggerFactory.getLogger(SharedFlowUnitTest::class.java)
    }

    @Test
    fun mutableSharedFlowTest() {
        logger.info("Starting test!")

        val coroutineScope = CoroutineScope(EmptyCoroutineContext)

        val events = MutableSharedFlow<Int>(1, 0)
        val readOnlyEvents = events.asSharedFlow()

        logger.debug("collection started")
        val job1 = coroutineScope.launch {
            readOnlyEvents.collect {
                logger.info("collected: {}", it)
            }
        }

        sleep(10_000)

        logger.debug("emition started")
        logger.debug("{}", events.tryEmit(10))
        logger.debug("{}", events.tryEmit(9))
        logger.debug("{}", events.tryEmit(8))
        logger.debug("{}", events.tryEmit(7))
        logger.debug("{}", events.tryEmit(6))
        logger.debug("{}", events.tryEmit(5))
        logger.debug("{}", events.tryEmit(4))
        logger.debug("{}", events.tryEmit(3))
        logger.debug("{}", events.tryEmit(2))
        logger.debug("{}", events.tryEmit(1))

        logger.debug("waiting for emition and collection to finish")
        runBlocking {
            job1.join()
        }

        logger.info("Test completed!")
    }

    @Test
    fun singleThreadedContextTest() {
        logger.info("Starting test!")

        val coroutineScope = CoroutineScope(newSingleThreadContext("Test"))

        logger.debug("job1 starting")
        val job1 = coroutineScope.launch {
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