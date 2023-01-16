package com.jjbaksa.domain.usecase

import com.jjbaksa.domain.base.BaseState
import com.jjbaksa.domain.repository.ShopRepository
import javax.inject.Inject

class GetTrendingKeywordUseCase @Inject constructor(
    private val shopRepository: ShopRepository
) {
    suspend operator fun invoke(): BaseState {
        return shopRepository.getTrendingKeyword()
    }
}
