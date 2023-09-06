package com.jjbaksa.domain.repository

import com.jjbaksa.domain.base.RespResult
import com.jjbaksa.domain.model.user.User
import com.jjbaksa.domain.model.user.FindPasswordReq
import com.jjbaksa.domain.model.user.Login
import com.jjbaksa.domain.model.user.SignUpReq
import com.jjbaksa.domain.model.user.SignUpResp
import com.jjbaksa.domain.model.user.WithdrawalReasonReq
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getUserMe(): Flow<Result<User>>
    suspend fun postLogin(
        account: String,
        password: String,
        isAutoLogin: Boolean
    ): Flow<Result<Login>>

    suspend fun postUserEmailId(email: String, onError: (String) -> Unit): Flow<Result<Boolean>>
    suspend fun getUserId(
        email: String,
        code: String,
        onError: (String) -> Unit
    ): Flow<Result<String>>

    suspend fun postUserEmailPassword(
        id: String,
        email: String,
        onError: (String) -> Unit
    ): Flow<Result<Boolean>>

    suspend fun postUserPassword(
        findPasswordReq: FindPasswordReq,
        onError: (String) -> Unit
    ): Flow<Result<Boolean>>

    suspend fun setNewPassword(password: String, onError: (String) -> Unit): Flow<Result<Boolean>>
    suspend fun setNewNickname(nickname: String): Flow<Result<User>>
    suspend fun editUserProfile(profile: String): Flow<Result<User>>
    suspend fun postUserCheckPassword(
        password: String,
        onError: (String) -> Unit
    ): Flow<Result<Boolean>>

    suspend fun postSignUp(signUpReq: SignUpReq): SignUpResp?
    suspend fun checkAccountAvailable(account: String): RespResult<Boolean>
    suspend fun postUserWithdrawReason(
        withdrawalReason: WithdrawalReasonReq,
        onError: (String) -> Unit
    ): Flow<Result<Boolean>>

    suspend fun deleteUserMe(onError: (String) -> Unit): Flow<Result<Boolean>>
    suspend fun logout()
    fun getAutoLoginFlag(): Boolean
    fun getAccount(): String
    fun getNickname(): String
    fun getFollowers(): Int
    fun getProfileImage(): String
    fun getAccessToken(): String
}
