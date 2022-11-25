package com.jjbaksa.domain.usecase

import com.jjbaksa.domain.repository.ShopRepository
import com.jjbaksa.domain.resp.shop.TrendingResult
import javax.inject.Inject

class GetTrendingsUseCase @Inject constructor(
    private val shopRepository: ShopRepository
) {
    suspend operator fun invoke(): TrendingResult? {
        return shopRepository.getTrendings()
    }
}
