package com.jjbaksa.domain.usecase

import com.jjbaksa.domain.base.RespResult
import com.jjbaksa.domain.repository.ShopRepository
import com.jjbaksa.domain.resp.shop.TrendingResult
import javax.inject.Inject

class GetTrendingsUseCase @Inject constructor(
    private val shopRepository: ShopRepository
) {
    suspend operator fun invoke(): RespResult<TrendingResult> {
        return shopRepository.getTrendings()
    }
}
