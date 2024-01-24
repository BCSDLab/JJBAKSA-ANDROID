package com.jjbaksa.domain.usecase

import com.jjbaksa.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSearchHistoryUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke(): Flow<Result<List<String>>> {
        return searchRepository.getSearchHistory()
    }

    suspend fun saveSearchHistory(keyword: String) {
        searchRepository.saveSearchHistory(keyword)
    }

    suspend fun deleteSearchHistory(keyword: String) {
        searchRepository.deleteSearchHistory(keyword)
    }
}