package com.demo.googlecalendar.presentation.utils

import androidx.annotation.Keep

sealed class UIState<T> {
    class Loading<T> : UIState<T>()
    @Keep
    data class Success<T>(val data: T) : UIState<T>()
    @Keep
    data class Error<T>(val error: Throwable) : UIState<T>()
}