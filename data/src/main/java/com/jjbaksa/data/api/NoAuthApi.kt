package com.jjbaksa.data.api

import com.jjbaksa.data.model.findid.FindIdResp
import com.jjbaksa.domain.resp.user.LoginReq
import com.jjbaksa.data.model.user.LoginResp
import com.jjbaksa.data.model.user.UserResp
import com.jjbaksa.domain.resp.user.FindPasswordReq
import com.jjbaksa.domain.resp.user.SignUpReq
import com.jjbaksa.domain.resp.user.SignUpResp
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.PATCH
import retrofit2.http.Header

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
    suspend fun getEmailCodeNumber(
        @Query("email") userEmail: String
    ): Response<Unit>

    @GET("user/account")
    suspend fun findId(
        @Query("email") userEmail: String,
        @Query("code") codeNumber: String
    ): Response<FindIdResp>

    @POST("user/password")
    suspend fun findPassword(
        @Body findPasswordReq: FindPasswordReq
    ): Response<String>

    @PATCH("user/password")
    suspend fun changePassword(
        @Header("Authorization") token: String,
        @Query("password") userPassword: String
    ): Response<UserResp>
}
