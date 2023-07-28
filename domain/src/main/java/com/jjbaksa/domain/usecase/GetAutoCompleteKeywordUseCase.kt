package com.jjbaksa.domain.usecase

import com.jjbaksa.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAutoCompleteKeywordUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke(keyword: String): Flow<Result<List<String>>> {
        return searchRepository.getSearchKeyword(keyword)
    }
}
