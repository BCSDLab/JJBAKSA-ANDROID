package com.jjbaksa.domain.usecase

import com.jjbaksa.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAutoCompleteKeywordUseCase @Inject constructor(
    private val searchRepository: SearchRepository,
) {
    suspend operator fun invoke(
        keyword: String,
        lat: Double,
        lng: Double,
    ): Flow<Result<List<String>>> {
        return if (keyword.isNotEmpty()) searchRepository.getSearchKeyword(keyword, lat, lng)
        else flow { emit(Result.success(listOf())) }
    }
}
