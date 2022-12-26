package com.jjbaksa.domain.usecase

import com.jjbaksa.domain.base.RespResult
import com.jjbaksa.domain.repository.ShopRepository
import com.jjbaksa.domain.resp.shop.ShopsResult
import javax.inject.Inject

class SearchShopsUseCase @Inject constructor(
    private val shopRepository: ShopRepository
) {
    suspend operator fun invoke(
        keyword: String,
        x: Double,
        y: Double,
        page: Int,
        size: Int
    ): RespResult<ShopsResult> {
        return shopRepository.searchShops(keyword, x, y, page, size)
    }
}
