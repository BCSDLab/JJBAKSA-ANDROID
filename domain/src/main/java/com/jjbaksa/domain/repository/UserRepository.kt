package com.jjbaksa.domain.repository

import com.jjbaksa.domain.base.RespResult
import com.jjbaksa.domain.resp.user.FindPasswordReq
import com.jjbaksa.domain.resp.user.FormatResp
import com.jjbaksa.domain.resp.user.LoginResult
import com.jjbaksa.domain.resp.user.SignUpReq
import com.jjbaksa.domain.resp.user.SignUpResp
import com.jjbaksa.domain.resp.user.WithdrawalReasonReq

interface UserRepository {
    suspend fun postSignUp(signUpReq: SignUpReq): SignUpResp?
    suspend fun checkAccountAvailable(account: String): RespResult<Boolean>
    suspend fun postLogin(
        account: String,
        password: String,
        isAutoLogin: Boolean,
        onResult: (LoginResult) -> Unit
    )

    suspend fun checkAuthEmail(email: String): FormatResp
    suspend fun checkPassword(password: String): FormatResp
    suspend fun getPasswordVerificationCode(id: String, email: String): FormatResp
    suspend fun findAccount(email: String, code: String): FormatResp
    suspend fun findPassword(user: FindPasswordReq): FormatResp
    suspend fun setNewPassword(password: String): FormatResp
    suspend fun setNewNickname(nickname: String): FormatResp
    suspend fun me(): RespResult<Boolean>
    suspend fun editUserProfileImage(photo: String): RespResult<Boolean>
    suspend fun saveWithdrawalReason(withdrawalReason: WithdrawalReasonReq): RespResult<Boolean>
    suspend fun deleteUser(): RespResult<Boolean>
    suspend fun singOut()
    fun getAutoLoginFlag(): Boolean
    fun getAccount(): String
    fun getNickname(): String
    fun getFollowers(): Int
    fun getProfileImage(): String
    fun getPassword(): String
    fun getAccessToken(): String
}
