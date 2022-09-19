package com.jjbaksa.data.api

import com.jjbaksa.data.resp.user.SignUpReq
import com.jjbaksa.data.resp.user.SignUpResp
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface NoAuthApi {
    @POST("user")
    suspend fun signUp(
        @Body singUpReq: SignUpReq
    ): Response<SignUpResp>
}
