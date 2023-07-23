package com.jjbaksa.data.api

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
}
