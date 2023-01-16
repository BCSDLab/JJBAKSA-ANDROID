package com.jjbaksa.data.repository

import com.jjbaksa.data.datasource.remote.ShopRemoteDataSource
import com.jjbaksa.domain.base.BaseState
import com.jjbaksa.domain.repository.ShopRepository
import javax.inject.Inject

class ShopRepositoryImpl @Inject constructor(
    private val shopRemoteDataSource: ShopRemoteDataSource
) : ShopRepository {
    override suspend fun getTrendingKeyword(): BaseState {
        val resp = shopRemoteDataSource.getTrendingKeyword()
        return if (resp.isSuccessful) {
            BaseState.Success(resp.body()!!.trendings.map { "#$it  " })
        } else {
            BaseState.Fail(resp.body()!!.errorMessage)
        }
    }
}
