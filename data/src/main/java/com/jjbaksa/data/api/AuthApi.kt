package com.jjbaksa.data.api

import com.jjbaksa.domain.resp.findid.FindIdResp
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthApi{
    @POST("user/email")
    fun getFindIdCodeNumber(
        @Query("email") userEmail: String
    ): Call<Unit>

    @GET("user/account")
    fun findAccount(
        @Query("email") userEmail: String,
        @Query("code") codeNumber: String
    ): Call<FindIdResp>
}

