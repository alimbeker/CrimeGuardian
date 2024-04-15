package com.example.crimeguardian.presentation

import kotlinx.coroutines.*

object CoroutineManager {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun launchCoroutine(block: suspend () -> Unit) {
        coroutineScope.launch {
            block()
        }
    }

    fun cancelCoroutines() {
        coroutineScope.cancel()
    }
}
