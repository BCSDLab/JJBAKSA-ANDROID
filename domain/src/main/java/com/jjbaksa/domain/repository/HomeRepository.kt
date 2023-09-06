package com.jjbaksa.domain.repository

interface HomeRepository {
    fun getMyInfoAutoLogin(): Boolean
    fun getMyInfoAccount(): String
    fun getMyInfoNickname(): String
    fun getMyInfoFollowers(): Int
    fun getMyInfoReviews(): Int
    fun getMyInfoProfileImage(): String
    fun getMyInfoToken(): String
}
