package com.jjbaksa.data.datasource.local

import com.jjbaksa.data.datasource.FindIdDataSource
import retrofit2.Response
import javax.inject.Inject

class FindIdLocalDataSource @Inject constructor() : FindIdDataSource {
    override suspend fun checkAuthEmail(email: String): Response<Unit> {
        TODO("Not yet implemented")
    }
}
