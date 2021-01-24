package com.kodepad.kotlinx.coroutines.flow

import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import kotlin.test.Test
import kotlin.test.assertEquals

class FlowUnitTest {
    companion object {
        private val logger = LoggerFactory.getLogger(FlowUnitTest::class.java)
    }

    @Test
    fun testMultiPathFlow() {
        logger.info("Starting test!")

        val integerFlow = flow<Int> {
            emit(10)
            emit(9)
            emit(8)
            emit(7)
            emit(6)
            emit(5)
            emit(4)
            emit(3)
            emit(2)
            emit(1)
        }

        val flow1 = integerFlow.map {
            logger.info("Path 1: {}", it)
        }

        val flow2 = integerFlow.map {
            logger.info("Path 2: {}", it)
        }

        runBlocking {
            flow1.collect {  }
            flow2.collect {  }
        }

        logger.info("Test completed!")
    }
}