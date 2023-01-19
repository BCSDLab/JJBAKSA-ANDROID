package com.jjbaksa.data.datasource

import com.jjbaksa.data.model.findid.FindIdResp
import retrofit2.Response

interface FindIdDataSource {
    suspend fun checkAuthEmail(email: String): Response<Unit>
    suspend fun findAccount(email: String, code: String): Response<FindIdResp>
}
