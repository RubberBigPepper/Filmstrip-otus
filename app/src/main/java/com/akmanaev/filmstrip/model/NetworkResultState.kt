package com.akmanaev.filmstrip.model


sealed class NetworkResultState<out R> private constructor() {
    data class Success<T>(val data: T) : NetworkResultState<T>()
    data class Error(val error: Exception) : NetworkResultState<Nothing>()
    data object Loading : NetworkResultState<Nothing>()
}