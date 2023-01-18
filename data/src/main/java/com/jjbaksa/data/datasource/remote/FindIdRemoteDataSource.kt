package com.jjbaksa.data.datasource.remote

import com.jjbaksa.data.api.AuthApi
import com.jjbaksa.data.api.NoAuthApi
import com.jjbaksa.data.datasource.FindIdDataSource
import retrofit2.Response
import javax.inject.Inject

class FindIdRemoteDataSource @Inject constructor(
    private val authApi: AuthApi,
    private val noAuthApi: NoAuthApi
) : FindIdDataSource {
    override suspend fun checkAuthEmail(email: String): Response<Unit> {
        return noAuthApi.getFindIdCodeNumber(email)
    }
}
