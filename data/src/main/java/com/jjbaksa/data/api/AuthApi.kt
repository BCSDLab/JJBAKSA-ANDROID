package com.jjbaksa.data.api

import retrofit2.http.GET
import com.jjbaksa.data.model.user.UserResp
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.Query

interface AuthApi {
    @GET("user/me")
    suspend fun userMe(): Response<UserResp>
    @PATCH("user/password")
    suspend fun changePassword(
        @Query("password") userPassword: String
    ): Response<UserResp>
}
