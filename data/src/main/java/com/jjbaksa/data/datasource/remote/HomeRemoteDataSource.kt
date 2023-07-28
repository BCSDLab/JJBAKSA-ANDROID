package com.jjbaksa.data.datasource.remote

import com.jjbaksa.data.api.AuthApi
import com.jjbaksa.data.api.NoAuthApi
import com.jjbaksa.data.datasource.HomeDataSource
import javax.inject.Inject

class HomeRemoteDataSource @Inject constructor(
    private val authApi: AuthApi,
    private val noAuthApi: NoAuthApi
) : HomeDataSource {
    override fun getUserAutoLogin(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getUserAccount(): String {
        TODO("Not yet implemented")
    }

    override fun getUserNickname(): String {
        TODO("Not yet implemented")
    }

    override fun getUserFollowers(): Int {
        TODO("Not yet implemented")
    }

    override fun getUserProfileImage(): String {
        TODO("Not yet implemented")
    }

    override fun getUserToken(): String {
        TODO("Not yet implemented")
    }
}
