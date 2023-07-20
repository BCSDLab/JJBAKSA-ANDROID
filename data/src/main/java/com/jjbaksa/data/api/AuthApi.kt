package com.jjbaksa.data.api

import retrofit2.http.GET
import com.jjbaksa.data.model.user.UserResp
import okhttp3.MultipartBody
import retrofit2.Response
import com.jjbaksa.domain.resp.user.PasswordAndNicknameReq
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.Part

interface AuthApi {
    @GET("user/me")
    suspend fun userMe(): Response<UserResp>
    @Multipart
    @PATCH("user/profile")
    suspend fun editUserProfileImage(
        @Part profile: MultipartBody.Part
    ): Response<UserResp>
}
