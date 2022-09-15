package com.benshapiro.composeexamples.data

import javax.inject.Singleton

@Singleton
data class DataOrException<T, E : Exception?>(
    var data: T? = null,
    var e: E? = null
)
