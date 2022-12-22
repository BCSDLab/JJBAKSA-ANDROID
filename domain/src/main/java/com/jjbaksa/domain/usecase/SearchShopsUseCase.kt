package com.jjbaksa.domain.usecase

import com.jjbaksa.domain.repository.ShopRepository
import com.jjbaksa.domain.resp.shop.ShopsResp
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
    ): ShopsResp? {
        return shopRepository.searchShops(keyword, x, y, page, size)
    }
}
