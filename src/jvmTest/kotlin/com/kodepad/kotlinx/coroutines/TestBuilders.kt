package com.kodepad.kotlinx.coroutines

import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking

actual fun <T> runBlockingTest(context: CoroutineContext, block: suspend CoroutineScope.() -> T): T = runBlocking(context, block)
