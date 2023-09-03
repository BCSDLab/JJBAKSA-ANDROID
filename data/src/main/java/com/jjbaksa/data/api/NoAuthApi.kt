package com.jjbaksa.data.api

import com.jjbaksa.data.model.inquiry.InquiryResp
import com.jjbaksa.data.model.post.PostDetailResp
import com.jjbaksa.data.model.post.PostResp
import com.jjbaksa.data.model.search.AutoKeywordResp
import com.jjbaksa.data.model.search.LocationBody
import com.jjbaksa.data.model.search.SearchShopResp
import com.jjbaksa.data.model.search.TrendResp
import com.jjbaksa.domain.model.user.LoginReq
import com.jjbaksa.data.model.user.LoginResp
import com.jjbaksa.data.model.user.UserResp
import com.jjbaksa.domain.model.user.FindPasswordReq
import com.jjbaksa.domain.model.user.PasswordAndNicknameReq
import com.jjbaksa.domain.model.user.SignUpReq
import com.jjbaksa.domain.model.user.SignUpResp
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.Path

interface NoAuthApi {
    @POST("user/login")
    suspend fun postLogin(
        @Body loginReq: LoginReq
    ): Response<LoginResp>

    @POST("user/email/account")
    suspend fun postUserEmailId(
        @Query("email") userEmail: String
    ): Response<Unit>

    @GET("user/account")
    suspend fun getUserId(
        @Query("email") userEmail: String,
        @Query("code") codeNumber: String
    ): Response<UserResp>

    @POST("user/email/password")
    suspend fun postUserEmailPassword(
        @Query("account") id: String,
        @Query("email") email: String
    ): Response<Unit>

    @PATCH("user/me")
    suspend fun patchUserMe(
        @Header("Authorization") token: String,
        @Body passwordAndNicknameReq: PasswordAndNicknameReq
    ): Response<UserResp>

    @POST("user/password")
    suspend fun postUserPassword(
        @Body findPasswordReq: FindPasswordReq
    ): Response<String>

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

    @GET("trending")
    suspend fun getTrending(): Response<TrendResp>

    @GET("auto-complete/{word}")
    suspend fun getSearchKeyword(@Path("word") word: String): Response<AutoKeywordResp>

    @POST("shops")
    suspend fun getShops(
        @Query("keyword") keyword: String,
        @Body locationBody: LocationBody
    ): Response<SearchShopResp>

    @POST("shops/page/{page_token}")
    suspend fun getShopsPage(
        @Path("page_token") pageToken: String,
        @Body locationBody: LocationBody
    ): Response<SearchShopResp>

    @GET("inquiry")
    suspend fun getInquiry(
        @Query("idCursor") idCursor: String?,
        @Query("dateCursor") dateCursor: String?,
        @Query("size") size: Int,
    ): Response<InquiryResp>

    @GET("post")
    suspend fun getPost(
        @Query("idCursor") idCursor: String,
        @Query("dateCursor") dateCursor: String,
        @Query("size") size: Int
    ): Response<PostResp>

    @GET("post/{post-id}")
    suspend fun getPostDetail(
        @Path("post-id") postId: Int
    ): Response<PostDetailResp>
}
