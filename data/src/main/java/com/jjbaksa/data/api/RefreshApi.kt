package com.jjbaksa.data.api
import com.jjbaksa.data.model.user.LoginResp
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST

interface RefreshApi {
    @GET("user/refresh")
    suspend fun getRefreshAuth(): Response<LoginResp>
}
