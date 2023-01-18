package com.jjbaksa.domain.repository

import com.jjbaksa.domain.base.RespResult

interface FindIdRepository {
    suspend fun checkAuthEmail(email: String): RespResult<Boolean>
}
