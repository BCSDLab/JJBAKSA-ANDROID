package com.jjbaksa.data.repository

import com.jjbaksa.data.datasource.local.HomeLocalDataSource
import com.jjbaksa.data.datasource.remote.HomeRemoteDataSource
import com.jjbaksa.domain.repository.HomeRepository
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val homeRemoteDataSource: HomeRemoteDataSource,
    private val homeLocalDataSource: HomeLocalDataSource
) : HomeRepository {
    override fun getMyInfoAutoLogin(): Boolean {
        return homeLocalDataSource.getUserAutoLogin()
    }

    override fun getMyInfoAccount(): String {
        return homeLocalDataSource.getUserAccount()
    }

    override fun getMyInfoNickname(): String {
        return homeLocalDataSource.getUserNickname()
    }

    override fun getMyInfoFollowers(): Int {
        return homeLocalDataSource.getUserFollowers()
    }

    override fun getMyInfoProfileImage(): String {
        return homeLocalDataSource.getUserProfileImage()
    }

    override fun getMyInfoToken(): String {
        return homeLocalDataSource.getUserToken()
    }
}
