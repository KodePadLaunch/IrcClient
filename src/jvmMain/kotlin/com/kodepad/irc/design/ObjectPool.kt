package com.kodepad.irc.design

interface ObjectPool<T> {
    fun acquireObject(): T
    fun releaseObject(reusableObject: T)
    fun setMaxPoolSize(size: Int)
}