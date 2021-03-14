package com.kodepad.kotlinx.coroutines

import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlinx.coroutines.CoroutineScope

expect fun <T> runBlockingTest(context: CoroutineContext = EmptyCoroutineContext, block: suspend CoroutineScope.() -> T): T
