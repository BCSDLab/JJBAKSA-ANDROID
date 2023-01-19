package com.jjbaksa.data.datasource.local

import com.jjbaksa.data.datasource.FindIdDataSource
import com.jjbaksa.data.model.findid.FindIdResp
import retrofit2.Response
import javax.inject.Inject

class FindIdLocalDataSource @Inject constructor() : FindIdDataSource {
    override suspend fun checkAuthEmail(email: String): Response<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun findAccount(email: String, code: String): Response<FindIdResp> {
        TODO("Not yet implemented")
    }
}
