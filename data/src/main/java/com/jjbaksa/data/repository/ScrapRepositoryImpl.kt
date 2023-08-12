package com.jjbaksa.data.repository

import com.jjbaksa.data.datasource.remote.ScrapRemoteDataSource
import com.jjbaksa.data.mapper.toShopScrap
import com.jjbaksa.data.model.apiCall
import com.jjbaksa.domain.repository.ScrapRepository
import com.jjbaksa.domain.resp.scrap.ShopScrap
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ScrapRepositoryImpl @Inject constructor(
    private val scrapRemoteDataSource: ScrapRemoteDataSource
): ScrapRepository {
    override suspend fun getShopScrap(scrapId: Int): Flow<Result<ShopScrap>> {
        return apiCall(
            call = { scrapRemoteDataSource.getShopScrap(scrapId) },
            mapper = {
                if (it.isSuccessful) {
                    it.body()!!.toShopScrap()
                } else {
                    ShopScrap()
                }
            }
        )
    }
}