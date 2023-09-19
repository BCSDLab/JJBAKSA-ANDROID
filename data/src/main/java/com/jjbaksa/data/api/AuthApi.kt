package com.jjbaksa.data.api

import com.jjbaksa.data.model.follower.FollowerReviewShopsResp
import com.jjbaksa.data.model.inquiry.InquiryContentResp
import com.jjbaksa.data.model.inquiry.InquiryResp
import com.jjbaksa.data.model.shop.ShopsMapsResp
import com.jjbaksa.data.model.review.MyReviewShopsResp
import com.jjbaksa.data.model.review.ReviewShopLastDateResp
import com.jjbaksa.data.model.pin.ShopDetailResp
import com.jjbaksa.data.model.review.ReviewShopDetailResp
import com.jjbaksa.data.model.review.ReviewShopResp
import com.jjbaksa.data.model.scrap.AddShopScrapBodyReq
import com.jjbaksa.data.model.scrap.AddShopScrapResp
import com.jjbaksa.data.model.scrap.ScrapsResp
import com.jjbaksa.data.model.scrap.ShopScrapResp
import com.jjbaksa.data.model.search.LocationBody
import retrofit2.http.GET
import com.jjbaksa.data.model.user.UserResp
import okhttp3.MultipartBody
import retrofit2.Response
import com.jjbaksa.domain.model.user.PasswordAndNicknameReq
import com.jjbaksa.domain.model.user.WithdrawalReasonReq
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
    suspend fun getUserMe(): Response<UserResp>
    @PATCH("user/me")
    suspend fun setUserNickname(
        @Body passwordAndNicknameReq: PasswordAndNicknameReq
    ): Response<UserResp>
    @DELETE("user/me")
    suspend fun deleteUserMe(): Response<Unit>
    @Multipart
    @PATCH("user/profile")
    suspend fun patchUserProfile(
        @Part profile: MultipartBody.Part
    ): Response<UserResp>
    @POST("user/withdraw-reason")
    suspend fun postUserWithdrawReason(
        @Body withdrawalReason: WithdrawalReasonReq
    ): Response<Unit>
    @POST("user/check-password")
    suspend fun postUserCheckPassword(
        @Query("password") password: String
    ): Response<UserResp>
    @GET("shops/pin/{place_id}")
    suspend fun getShopDetail(
        @Path("place_id") placeId: String
    ): Response<ShopDetailResp>
    @POST("shops/maps")
    suspend fun getShopsMaps(
        @Query("options_friend") optionsFriend: Int,
        @Query("options_nearby") optionsNearby: Int,
        @Query("options_scrap") optionsScrap: Int,
        @Body locationBody: LocationBody
    ): Response<List<ShopsMapsResp>>
    @GET("scraps/shops/{scrap_id}")
    suspend fun getShopScrap(
        @Path("scrap_id") scrapId: Int
    ): Response<ShopScrapResp>
    @POST("scraps")
    suspend fun postShopScrap(
        @Body addShopScrapBody: AddShopScrapBodyReq
    ): Response<AddShopScrapResp>
    @GET("scraps")
    suspend fun getScraps(
        @Query("user") user: Int?,
        @Query("cursor") cursor: Int?,
        @Query("size") size: Int
    ): Response<ScrapsResp>
    @Multipart
    @POST("review")
    suspend fun postReview(
        @Query("placeId") placeId: String,
        @Query("content") content: String,
        @Query("rate") rate: Int,
        @Part reviewImages: List<MultipartBody.Part>
    ): Response<ReviewShopDetailResp>
    @GET("review/shops")
    suspend fun getMyReviewShops(
        @Query("cursor") cursor: Int?,
        @Query("size") size: Int
    ): Response<ReviewShopResp>
    @GET("review/shop/{place-id}")
    suspend fun getMyReviewShops(
        @Path("place-id") placeId: String,
        @Query("idCursor") idCursor: Int?,
        @Query("dateCursor") dateCursor: String?,
        @Query("rateCursor") rateCursor: Int?,
        @Query("size") size: Int?,
        @Query("direction") direction: String?,
        @Query("sort") sort: String?,
    ): Response<MyReviewShopsResp>
    @GET("review/last-date/shop/{place-id}")
    suspend fun getMyReviewShopLastDate(
        @Path("place-id") placeId: String
    ): Response<ReviewShopLastDateResp>
    @GET("review/followers/last-date/shop/{place-id}")
    suspend fun getFollowerReviewShopsLastDate(
        @Path("place-id") placeId: String
    ): Response<ReviewShopLastDateResp>
    @GET("review/followers/shop/{place-id}")
    suspend fun getFollowerReviewShops(
        @Path("place-id") placeId: String,
        @Query("idCursor") idCursor: Int?,
        @Query("dateCursor") dateCursor: String?,
        @Query("rateCursor") rateCursor: Int?,
        @Query("size") size: Int?,
        @Query("direction") direction: String?,
        @Query("sort") sort: String?
    ): Response<FollowerReviewShopsResp>
    @GET("inquiry")
    suspend fun getInquiry(
        @Query("idCursor") idCursor: Long?,
        @Query("dateCursor") dateCursor: String?,
        @Query("size") size: Int,
    ): Response<InquiryResp>
    @GET("inquiry/me")
    suspend fun getInquiryMe(
        @Query("idCursor") idCursor: Long?,
        @Query("dateCursor") dateCursor: String?,
        @Query("size") size: Int,
    ): Response<InquiryResp>
    @Multipart
    @POST("inquiry")
    suspend fun postInquiry(
        @Query("title") title: String,
        @Query("content") content: String,
        @Query("isSecret") isSecret: Boolean,
        @Part inquiryImages: List<MultipartBody.Part>
    ): Response<InquiryContentResp>
}
