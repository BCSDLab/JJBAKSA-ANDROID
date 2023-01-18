package com.jjbaksa.data.repository

import com.jjbaksa.data.datasource.local.FindIdLocalDataSource
import com.jjbaksa.data.datasource.remote.FindIdRemoteDataSource
import com.jjbaksa.data.mapper.RespMapper
import com.jjbaksa.domain.base.ErrorType
import com.jjbaksa.domain.base.RespResult
import com.jjbaksa.domain.repository.FindIdRepository
import javax.inject.Inject

class FindIdRepositoryImpl @Inject constructor(
    private val findIdRemoteDataSource: FindIdRemoteDataSource,
    private val findIdLocalDataSource: FindIdLocalDataSource
) : FindIdRepository {
    override suspend fun checkAuthEmail(email: String): RespResult<Boolean> {
        val result = findIdRemoteDataSource.checkAuthEmail(email)
        return if (result.isSuccessful) {
            RespResult.Success(result.isSuccessful)
        } else {
            val errorBodyJson = result.errorBody()!!.string()
            val errorBody = RespMapper.errorMapper(errorBodyJson)
            RespResult.Error(ErrorType(errorBody.errorMessage, errorBody.code))
        }
    }
}
