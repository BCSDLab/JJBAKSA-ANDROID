package com.jjbaksa.data.api

import com.jjbaksa.data.model.user.UserResp
import retrofit2.Response
import retrofit2.http.GET

interface AuthApi {
    @GET("user/me")
    suspend fun userMe(): Response<UserResp>
}
