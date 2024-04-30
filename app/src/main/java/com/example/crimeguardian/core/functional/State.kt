package com.example.crimeguardian.core.functional

import android.util.Log

sealed class State<out Data> {
    class Failure(val exception: Throwable) : State<Nothing>()
    data object Loading : State<Nothing>()
    data object Initial : State<Nothing>()
    class Data<out Data>(val data: Data) : State<Data>()
}