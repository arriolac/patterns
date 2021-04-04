package me.chrisarriola.creativecoding

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

fun intTicker(delayInMillis: Long): Flow<Int> = flow {
    var value = 0
    emit(value++)
    while(true) {
        delay(delayInMillis)
        emit(value++)
    }
}

fun floatTicker(delayInMillis: Long, increment: Float) = flow {
    var value = 0F
    emit(value)
    value += increment
    while(true) {
        delay(delayInMillis)
        emit(value)
        value += increment
    }
}