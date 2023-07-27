package com.jjbaksa.data.datasource

interface HomeDataSource {
    fun getUserAutoLogin(): Boolean
    fun getUserAccount(): String
    fun getUserNickname(): String
    fun getUserFollowers(): Int
    fun getUserProfileImage(): String
    fun getUserToken(): String
}
