package com.jjbaksa.data.api

import com.jjbaksa.data.model.findid.FindIdResp
import com.jjbaksa.data.model.search.AutoKeywordResp
import com.jjbaksa.data.model.search.LocationBody
import com.jjbaksa.data.model.search.SearchShopResp
import com.jjbaksa.data.model.search.TrendResp
import com.jjbaksa.domain.resp.user.LoginReq
import com.jjbaksa.data.model.user.LoginResp
import com.jjbaksa.data.model.user.UserResp
import com.jjbaksa.domain.resp.user.FindPasswordReq
import com.jjbaksa.domain.resp.user.PasswordAndNicknameReq
import com.jjbaksa.domain.resp.user.SignUpReq
import com.jjbaksa.domain.resp.user.SignUpResp
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.Path

interface NoAuthApi {
    @POST("user")
    suspend fun signUp(
        @Body signUpReq: SignUpReq
    ): Response<SignUpResp>

    @GET("user/exists")
    suspend fun checkIdAvailable(
        @Query("account") page: String
    ): Response<Unit>
    @POST("user/check-password")
    suspend fun checkPassword(
        @Header("Authorization") token: String,
        @Query("password") password: String
    ): Response<Unit>

    @POST("user/login")
    suspend fun login(
        @Body loginReq: LoginReq
    ): Response<LoginResp>

    @POST("user/email/account")
    suspend fun getEmailCodeNumber(
        @Query("email") userEmail: String
    ): Response<Unit>
    @POST("user/email/password")
    suspend fun getPasswordVerificationCode(
        @Query("account") id: String,
        @Query("email") email: String
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

    @PATCH("user/me")
    suspend fun setNewPassword(
        @Header("Authorization") token: String,
        @Body passwordAndNicknameReq: PasswordAndNicknameReq
    ): Response<UserResp>
    @GET("trending")
    suspend fun getTrending(): Response<TrendResp>

    @GET("auto-complete/{word}")
    suspend fun getSearchKeyword(@Path("word") word: String): Response<AutoKeywordResp>
    @POST("shops")
    suspend fun getShops(@Query("keyword") keyword: String, @Body locationBody: LocationBody): Response<SearchShopResp>
    @POST("shops/page/{page_token}")
    suspend fun getShopsPage(
        @Path("page_token") pageToken: String,
        @Body locationBody: LocationBody
    ): Response<SearchShopResp>
}
