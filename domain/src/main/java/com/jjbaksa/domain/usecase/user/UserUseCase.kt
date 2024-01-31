package com.jjbaksa.domain.usecase.user

import com.jjbaksa.domain.model.user.FindPasswordReq
import com.jjbaksa.domain.model.user.User
import com.jjbaksa.domain.repository.UserRepository
import com.jjbaksa.domain.model.user.Login
import com.jjbaksa.domain.model.user.UserList
import com.jjbaksa.domain.model.user.WithdrawalReasonReq
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend fun postLoginSNS(token: String, snsType: String): Result<Login> =
        userRepository.postLoginSNS(token, snsType)
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

    suspend fun postUserEmailCheck(email: String): Flow<Result<Login>> {
        return userRepository.postUserEmailCheck(email)
    }

    suspend fun postUserEmailId(
        email: String,
        onError: (String) -> Unit
    ): Flow<Result<Boolean>> {
        return userRepository.postUserEmailId(email, onError)
    }

    suspend fun getUserId(
        email: String,
        code: String,
        onError: (String) -> Unit
    ): Flow<Result<String>> {
        return userRepository.getUserId(email, code, onError)
    }

    suspend fun postUserEmailPassword(
        id: String,
        email: String,
        onError: (String) -> Unit
    ): Flow<Result<Boolean>> {
        return userRepository.postUserEmailPassword(id, email, onError)
    }

    suspend fun postUserPassword(
        findPasswordReq: FindPasswordReq,
        onError: (String) -> Unit
    ): Flow<Result<Boolean>> {
        return userRepository.postUserPassword(findPasswordReq, onError)
    }

    suspend fun setNewPassword(
        password: String,
        onError: (String) -> Unit
    ): Flow<Result<Boolean>> {
        return userRepository.setNewPassword(password, onError)
    }

    suspend fun setNewNickname(
        nickname: String
    ): Flow<Result<User>> {
        return userRepository.setNewNickname(nickname)
    }

    suspend fun editUserProfile(profile: String): Flow<Result<User>> {
        return userRepository.editUserProfile(profile)
    }

    suspend fun postUserCheckPassword(
        password: String,
        onError: (String) -> Unit
    ): Flow<Result<Boolean>> {
        return userRepository.postUserCheckPassword(password, onError)
    }

    suspend fun logout() {
        return userRepository.logout()
    }

    suspend fun clearDataStoreNoneAutoLogin() {
        userRepository.clearDataStoreNoneAutoLogin()
    }

    suspend fun postUserWithdrawalReason(
        withdrawalReason: WithdrawalReasonReq,
        onError: (String) -> Unit
    ): Flow<Result<Boolean>> {
        return userRepository.postUserWithdrawReason(withdrawalReason, onError)
    }

    suspend fun deleteUserMe(
        onError: (String) -> Unit
    ): Flow<Result<Boolean>> {
        return userRepository.deleteUserMe(onError)
    }

    suspend fun getUserSearch(
        keyWord: String,
        pageSize: Int?,
        cursor: Long?
    ): Flow<Result<UserList>> {
        return userRepository.getUserSearch(keyWord, pageSize, cursor)
    }
}
