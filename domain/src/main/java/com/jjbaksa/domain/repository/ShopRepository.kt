package com.jjbaksa.domain.repository

import com.jjbaksa.domain.base.BaseState

interface ShopRepository {
    suspend fun getTrendingKeyword(): BaseState
}
