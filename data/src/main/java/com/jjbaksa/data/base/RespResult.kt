package com.jjbaksa.data.base

sealed class RespResult<T> {
    data class Success<T>(val data: T) : RespResult<T>()
    data class Error<T>(val errorType: ErrorType) : RespResult<T>()
}
