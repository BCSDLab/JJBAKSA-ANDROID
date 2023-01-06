package com.jjbaksa.domain.usecase

import com.jjbaksa.domain.repository.ShopRepository
import javax.inject.Inject

class GetSearchHistoryUseCase @Inject constructor(
    private val shopRepository: ShopRepository
) {
    suspend operator fun invoke() = shopRepository.getSearchHistory()
}
