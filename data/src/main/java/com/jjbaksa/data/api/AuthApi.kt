package com.jjbaksa.data.api

import retrofit2.http.GET
import com.jjbaksa.data.model.user.UserResp
import retrofit2.Response

interface AuthApi {
    @GET("user/me")
    suspend fun userMe(): Response<UserResp>
}
