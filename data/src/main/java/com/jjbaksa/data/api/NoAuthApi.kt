package com.jjbaksa.data.api

import com.jjbaksa.data.model.pin.ShopDetailResp
import com.jjbaksa.data.model.post.PostDetailResp
import com.jjbaksa.data.model.post.PostResp
import com.jjbaksa.data.model.search.LocationBody
import com.jjbaksa.data.model.search.SearchShopResp
import com.jjbaksa.data.model.search.TrendResp
import com.jjbaksa.data.model.shop.ShopInfoResp
import com.jjbaksa.data.model.user.LoginResp
import com.jjbaksa.data.model.user.UserListResp
import com.jjbaksa.data.model.user.UserResp
import com.jjbaksa.domain.model.user.FindPasswordReq
import com.jjbaksa.domain.model.user.LoginReq
import com.jjbaksa.domain.model.user.PasswordAndNicknameReq
import com.jjbaksa.domain.model.user.SignUpReq
import com.jjbaksa.domain.model.user.SignUpResp
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface NoAuthApi {
    @POST("login/{sns-type}")
    suspend fun postLoginSNS(
        @Header("Authorization") token: String,
        @Path("sns-type") snsType: String
    ): LoginResp
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

    @GET("trending")
    suspend fun getTrending(): Response<TrendResp>

    @POST("shops/auto-complete")
    suspend fun getSearchKeyword(
        @Query("query") query: String,
        @Body locationBody: LocationBody
    ): Response<List<String>>

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

    @GET("post")
    suspend fun getPost(
        @Query("idCursor") idCursor: Int?,
        @Query("dateCursor") dateCursor: String?,
        @Query("size") size: Int
    ): Response<PostResp>

    @GET("post/{post-id}")
    suspend fun getPostDetail(
        @Path("post-id") postId: Int
    ): Response<PostDetailResp>

    @POST("user/authenticate")
    suspend fun postUserEmailCheck(
        @Query("email") userEmail: String
    ): Response<LoginResp>

    @GET("shops/{place-id}")
    suspend fun getShopInfo(
        @Path("place-id") placeId: String
    ): Response<ShopInfoResp>
}
