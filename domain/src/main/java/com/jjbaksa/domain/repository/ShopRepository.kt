package com.jjbaksa.domain.repository

import com.jjbaksa.domain.resp.shop.TrendingResult

interface ShopRepository {
    suspend fun getTrendings(): TrendingResult?
}