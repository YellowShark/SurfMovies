package ru.yellowshark.favoritemovies.ui.base

import java.lang.Exception

sealed class ViewState<T> {
    class Loading<T> : ViewState<T>()
    data class Success<T>(val data: T): ViewState<T>()
    data class Error<T>(val throwable: Throwable) : ViewState<T>()
}