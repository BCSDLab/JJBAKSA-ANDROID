package com.jjbaksa.data.repository

import com.jjbaksa.data.datasource.remote.ScrapRemoteDataSource
import com.jjbaksa.data.mapper.scrap.toAddShopScrap
import com.jjbaksa.data.mapper.scrap.toScraps
import com.jjbaksa.data.mapper.scrap.toShopScrap
import com.jjbaksa.data.model.apiCall
import com.jjbaksa.domain.repository.ScrapRepository
import com.jjbaksa.domain.model.scrap.AddShopScrap
import com.jjbaksa.domain.model.scrap.Scraps
import com.jjbaksa.domain.model.scrap.ShopScrap
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
                    it.body()?.toAddShopScrap() ?: AddShopScrap()
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
    ): Flow<Result<Scraps>> {
        return apiCall(
            call = { scrapRemoteDataSource.getScraps(user, cursor, size) },
            mapper = {
                if (it.isSuccessful) {
                    it.body()?.toScraps() ?: Scraps()
                } else {
                    Scraps()
                }
            }
        )
    }
}
