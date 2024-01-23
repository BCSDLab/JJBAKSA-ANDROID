package com.jjbaksa.jjbaksa.util

sealed interface UiState<out t> {
    object Empty : UiState<Nothing>
    object Loading : UiState<Nothing>
    data class Success<T>(
        val data: T,
    ) : UiState<T>

    data class Failure(
        val message: String,
    ) : UiState<Nothing>
}