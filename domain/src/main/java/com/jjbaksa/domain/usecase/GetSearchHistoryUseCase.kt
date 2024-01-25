package com.jjbaksa.domain.usecase

import com.google.gson.GsonBuilder
import com.jjbaksa.domain.model.user.Login
import com.jjbaksa.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetSearchHistoryUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {
    operator fun invoke(): List<String> {
        return searchRepository.getSearchHistory().toList()

    }

    suspend fun setSearchHistories(histories: List<String>) {
        searchRepository.setSearchHistories(histories.toJsonString())
    }

    private fun String.toList(): List<String> {
        return if(this != "[]")
            GsonBuilder().create().fromJson(this, Array<String>::class.java).toList()
        else listOf()
    }

    private fun List<String>.toJsonString(): String {
        return GsonBuilder().create().toJson(this)
    }
}