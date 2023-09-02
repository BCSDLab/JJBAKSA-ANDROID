package com.jjbaksa.domain.usecase.user

import com.jjbaksa.domain.model.user.User
import com.jjbaksa.domain.repository.UserRepository
import com.jjbaksa.domain.model.user.Login
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend fun getUserMe(): Flow<Result<User>> {
        return userRepository.getUserMe()
    }
    suspend fun postLogin(
        account: String,
        password: String,
        isAutoLogin: Boolean
    ): Flow<Result<Login>> {
        return userRepository.postLogin(account, password, isAutoLogin)
    }
}
