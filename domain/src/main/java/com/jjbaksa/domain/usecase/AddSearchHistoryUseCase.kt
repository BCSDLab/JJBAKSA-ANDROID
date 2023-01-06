package com.jjbaksa.domain.usecase

import com.jjbaksa.domain.repository.ShopRepository
import javax.inject.Inject

class AddSearchHistoryUseCase @Inject constructor(
    private val shopRepository: ShopRepository
) {
    suspend operator fun invoke(searchKeyword: String) {
        shopRepository.addSearchHistory(searchKeyword)
    }
}
