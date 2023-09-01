package com.jjbaksa.data.repository

import com.jjbaksa.data.datasource.remote.ScrapRemoteDataSource
import com.jjbaksa.data.mapper.toAddShopScrap
import com.jjbaksa.data.mapper.toGetScraps
import com.jjbaksa.data.mapper.toShopScrap
import com.jjbaksa.data.model.apiCall
import com.jjbaksa.domain.repository.ScrapRepository
import com.jjbaksa.domain.resp.scrap.AddShopScrap
import com.jjbaksa.domain.resp.scrap.GetScraps
import com.jjbaksa.domain.resp.scrap.ShopScrap
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ScrapRepositoryImpl @Inject constructor(
    private val scrapRemoteDataSource: ScrapRemoteDataSource
) : ScrapRepository {
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

    override suspend fun addShopScrap(
        directoryId: Int,
        placeId: String
    ): Flow<Result<AddShopScrap>> {
        return apiCall(
            call = { scrapRemoteDataSource.addShopScrap(directoryId, placeId) },
            mapper = {
                if (it.isSuccessful) {
                    it.body()!!.toAddShopScrap()
                } else {
                    AddShopScrap()
                }
            }
        )
    }

    override suspend fun getScraps(
        user: Int?,
        cursor: Int?,
        size: Int
    ): Flow<Result<GetScraps>> {
        return apiCall(
            call = { scrapRemoteDataSource.getScraps(user, cursor, size) },
            mapper = {
                if (it.isSuccessful) {
                    it.body()!!.toGetScraps()
                } else {
                    GetScraps()
                }
            }
        )
    }
}
