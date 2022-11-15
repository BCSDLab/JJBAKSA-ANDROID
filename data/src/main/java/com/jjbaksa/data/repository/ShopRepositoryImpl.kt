package com.jjbaksa.data.repository

import com.jjbaksa.data.datasource.local.ShopLocalDataSource
import com.jjbaksa.data.datasource.local.UserLocalDataSource
import com.jjbaksa.data.datasource.remote.ShopRemoteDataSource
import com.jjbaksa.data.datasource.remote.UserRemoteDataSource
import com.jjbaksa.data.mapper.TrendingMapper.mapToTrendingResult
import com.jjbaksa.domain.repository.ShopRepository
import com.jjbaksa.domain.resp.shop.TrendingResult
import javax.inject.Inject

class ShopRepositoryImpl @Inject constructor(
    private val shopRemoteDataSource: ShopRemoteDataSource,
    private val shopLocalDataSource: ShopLocalDataSource
) : ShopRepository {
    override suspend fun getTrendings(): TrendingResult? {
        val resp = shopRemoteDataSource.getTrendings().body()
        return resp?.mapToTrendingResult()
    }
}