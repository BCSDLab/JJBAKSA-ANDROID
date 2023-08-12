package com.jjbaksa.data.api

import com.jjbaksa.data.model.follower.FollowerShopReviewResp
import com.jjbaksa.data.model.map.MyReviewResp
import com.jjbaksa.data.model.map.ShopReviewLastDateResp
import com.jjbaksa.data.model.map.ShopReviewResp
import com.jjbaksa.data.model.pin.ShopDetailResp
import com.jjbaksa.data.model.scrap.ShopScrapResp
import retrofit2.http.GET
import com.jjbaksa.data.model.user.UserResp
import okhttp3.MultipartBody
import retrofit2.Response
import com.jjbaksa.domain.resp.user.PasswordAndNicknameReq
import com.jjbaksa.domain.resp.user.WithdrawalReasonReq
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface AuthApi {
    @GET("user/me")
    suspend fun userMe(): Response<UserResp>
    @PATCH("user/me")
    suspend fun setUserNickname(
        @Body item: PasswordAndNicknameReq
    ): Response<UserResp>
    @DELETE("user/me")
    suspend fun deleteUser(): Response<Unit>
    @Multipart
    @PATCH("user/profile")
    suspend fun editUserProfileImage(
        @Part profile: MultipartBody.Part
    ): Response<UserResp>
    @POST("user/withdraw-reason")
    suspend fun saveWithdrawalReason(
        @Body withdrawalReason: WithdrawalReasonReq
    ): Response<Unit>
    @GET("shops/pin/{place_id}")
    suspend fun getShopDetail(
        @Path("place_id") placeId: String
    ): Response<ShopDetailResp>
    @GET("scraps/shops/{scrap_id}")
    suspend fun getShopScrap(
        @Path("scrap_id") scrapId: Int
    ): Response<ShopScrapResp>
    @Multipart
    @POST("review")
    suspend fun setReview(
        @Query("placeId") placeId: String,
        @Query("content") content: String,
        @Query("rate") rate: Int,
        @Part reviewImages: List<MultipartBody.Part>
    ): Response<ShopReviewResp>
    @GET("review/shop/{place-id}")
    suspend fun getMyReview(
        @Path("place-id") placeId: String,
        @Query("idCursor") idCursor: Int?,
        @Query("dateCursor") dateCursor: String?,
        @Query("rateCursor") rateCursor: Int?,
        @Query("size") size: Int?,
        @Query("direction") direction: String?,
        @Query("sort") sort: String?,
    ): Response<MyReviewResp>
    @GET("review/last-date/shop/{place-id}")
    suspend fun getShopReviewLastDate(
        @Path("place-id") placeId: String
    ): Response<ShopReviewLastDateResp>
    @GET("review/followers/last-date/shop/{place-id}")
    suspend fun getShopFollowerReviewLastDate(
        @Path("place-id") placeId: String
    ): Response<ShopReviewLastDateResp>
    @GET("review/followers/shop/{place-id}")
    suspend fun getFollowerShopReview(
        @Path("place-id") placeId: String,
        @Query("idCursor") idCursor: Int?,
        @Query("dateCursor") dateCursor: String?,
        @Query("rateCursor") rateCursor: Int?,
        @Query("size") size: Int?,
        @Query("direction") direction: String?,
        @Query("sort") sort: String?
    ): Response<FollowerShopReviewResp>
}
