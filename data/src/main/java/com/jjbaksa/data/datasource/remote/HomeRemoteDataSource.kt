package com.jjbaksa.data.datasource.remote

import com.jjbaksa.data.api.AuthApi
import com.jjbaksa.data.api.NoAuthApi
import com.jjbaksa.data.datasource.HomeDataSource
import javax.inject.Inject

class HomeRemoteDataSource @Inject constructor(
    private val authApi: AuthApi,
    private val noAuthApi: NoAuthApi
) : HomeDataSource
