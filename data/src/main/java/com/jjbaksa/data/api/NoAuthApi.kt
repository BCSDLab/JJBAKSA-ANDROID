package com.jjbaksa.data.api

import com.jjbaksa.data.model.findid.FindIdResp
import com.jjbaksa.domain.resp.user.LoginReq
import com.jjbaksa.data.model.user.LoginResp
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
    ): Response<Unit>

    @POST("user/login")
    suspend fun login(
        @Body loginReq: LoginReq
    ): Response<LoginResp>

    @POST("user/email")
    suspend fun getFindIdCodeNumber(
        @Query("email") userEmail: String
    ): Response<Unit>

    @GET("user/account")
    suspend fun findId(
        @Query("email") userEmail: String,
        @Query("code") codeNumber: String
    ): Response<FindIdResp>
}
