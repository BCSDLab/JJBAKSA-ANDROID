package com.jjbaksa.data.api

import com.jjbaksa.domain.resp.user.SignUpReq
import com.jjbaksa.domain.resp.user.SignUpResp
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface NoAuthApi {
    @POST("user")
    suspend fun signUp(
        @Body signUpReq: SignUpReq
    ): Response<SignUpResp>

    @GET("user/exists")
    suspend fun checkIdAvailable(
        @Query("account") page: String
    ): Response<String>
}
