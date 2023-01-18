package com.jjbaksa.data.datasource

import retrofit2.Response

interface FindIdDataSource {
    suspend fun checkAuthEmail(email: String): Response<Unit>
}
