package com.jjbaksa.domain.repository

import com.jjbaksa.domain.base.RespResult
import com.jjbaksa.domain.model.user.User
import com.jjbaksa.domain.resp.user.FindPasswordReq
import com.jjbaksa.domain.resp.user.FormatResp
import com.jjbaksa.domain.model.user.Login
import com.jjbaksa.domain.resp.user.SignUpReq
import com.jjbaksa.domain.resp.user.SignUpResp
import com.jjbaksa.domain.resp.user.WithdrawalReasonReq
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getUserMe(): Flow<Result<User>>
    suspend fun postLogin(
        account: String,
        password: String,
        isAutoLogin: Boolean
    ): Flow<Result<Login>>
    suspend fun postUserEmailId(email: String, onError: (String) -> Unit): Flow<Result<Boolean>>
    suspend fun getUserId(email: String, code: String, onError: (String) -> Unit): Flow<Result<String>>
    suspend fun postSignUp(signUpReq: SignUpReq): SignUpResp?
    suspend fun checkAccountAvailable(account: String): RespResult<Boolean>
    suspend fun checkPassword(password: String): FormatResp
    suspend fun getPasswordVerificationCode(id: String, email: String): FormatResp
    suspend fun findPassword(user: FindPasswordReq): FormatResp
    suspend fun setNewPassword(password: String): FormatResp
    suspend fun setNewNickname(nickname: String): FormatResp
    suspend fun editUserProfileImage(photo: String): RespResult<Boolean>
    suspend fun saveWithdrawalReason(withdrawalReason: WithdrawalReasonReq): RespResult<Boolean>
    suspend fun deleteUser(): RespResult<Boolean>
    suspend fun singOut()
    fun getAutoLoginFlag(): Boolean
    fun getAccount(): String
    fun getNickname(): String
    fun getFollowers(): Int
    fun getProfileImage(): String
    fun getAccessToken(): String
}
