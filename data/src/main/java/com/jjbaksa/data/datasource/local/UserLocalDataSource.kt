package com.jjbaksa.data.datasource.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.jjbaksa.data.database.PreferenceKeys
import com.jjbaksa.data.database.UserDao
import com.jjbaksa.data.database.userDataStore
import com.jjbaksa.data.datasource.UserDataSource
import com.jjbaksa.data.model.findid.FindIdResp
import com.jjbaksa.data.model.user.LoginResp
import com.jjbaksa.data.model.user.UserResp
import com.jjbaksa.domain.resp.user.FindPasswordReq
import com.jjbaksa.domain.resp.user.LoginReq
import com.jjbaksa.domain.resp.user.PasswordAndNicknameReq
import com.jjbaksa.domain.resp.user.SignUpReq
import com.jjbaksa.domain.resp.user.SignUpResp
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import retrofit2.Response
import javax.inject.Inject

class UserLocalDataSource @Inject constructor(
    @ApplicationContext context: Context,
    private val userDao: UserDao
) : UserDataSource {
    private val dataStore = context.userDataStore
    override suspend fun postSignUp(signUpReq: SignUpReq): Response<SignUpResp>? {
        return null
    }

    override suspend fun checkAccountAvailable(account: String): Response<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun postLogin(loginReq: LoginReq): Response<LoginResp>? {
        return null
    }

    override suspend fun checkAuthEmail(email: String): Response<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun checkPassword(password: String, token: String): Response<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun findAccount(email: String, code: String): Response<FindIdResp> {
        TODO("Not yet implemented")
    }

    override suspend fun getPasswordVerificationCode(id: String, email: String): Response<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun findPassword(findPasswordReq: FindPasswordReq): Response<String> {
        TODO("Not yet implemented")
    }

    override suspend fun setNewPassword(token: String, item: PasswordAndNicknameReq): Response<UserResp> {
        TODO("Not yet implemented")
    }

    override suspend fun saveAccessToken(accessToken: String) {
        dataStore.edit {
            it[PreferenceKeys.ACCESS_TOKEN] = accessToken
        }
    }

    override suspend fun saveAccount(account: String) {
        dataStore.edit {
            it[PreferenceKeys.ACCOUNT] = account
        }
    }

    override suspend fun saveNickname(nickname: String) {
        dataStore.edit {
            it[PreferenceKeys.NICKNAME] = nickname
        }
    }

    override suspend fun saveFollowers(followers: Int) {
        dataStore.edit {
            it[PreferenceKeys.FOLLOWERS] = followers
        }
    }

    override suspend fun saveProfileImage(image: String) {
        dataStore.edit {
            it[PreferenceKeys.IMAGE] = image
        }
    }

    override suspend fun savePassword(password: String) {
        dataStore.edit {
            it[PreferenceKeys.PASSWORD] = password
        }
    }

    override suspend fun saveRefreshToken(refreshToken: String) {
        dataStore.edit {
            it[PreferenceKeys.REFRESH_TOKEN] = refreshToken
        }
    }

    override suspend fun saveAutoLogin(isAutoLogin: Boolean) {
        dataStore.edit {
            it[PreferenceKeys.AUTO_LOGIN] = isAutoLogin
        }
    }

    override suspend fun saveAuthPasswordToken(passwordToken: String) {
        dataStore.edit {
            it[PreferenceKeys.AUTH_PASSWORD_TOKEN] = passwordToken
        }
    }

    override fun getAutoLoginFlag(): Boolean {
        return runBlocking {
            dataStore.data.first()[PreferenceKeys.AUTO_LOGIN] ?: false
        }
    }

    override fun getAccount(): String {
        return runBlocking {
            dataStore.data.first()[PreferenceKeys.ACCOUNT] ?: ""
        }
    }

    override fun getNickname(): String {
        return runBlocking {
            dataStore.data.first()[PreferenceKeys.NICKNAME] ?: ""
        }
    }

    override fun getFollowers(): Int {
        return runBlocking {
            dataStore.data.first()[PreferenceKeys.FOLLOWERS] ?: 0
        }
    }

    override fun getProfileImage(): String {
        return runBlocking {
            dataStore.data.first()[PreferenceKeys.IMAGE] ?: ""
        }
    }

    override fun getPassword(): String {
        return runBlocking {
            dataStore.data.first()[PreferenceKeys.PASSWORD] ?: ""
        }
    }

    override fun getAccessToken(): String {
        return runBlocking {
            dataStore.data.first()[PreferenceKeys.ACCESS_TOKEN] ?: ""
        }
    }

    override fun getAuthPasswordToken(): String {
        return runBlocking {
            dataStore.data.first()[PreferenceKeys.AUTH_PASSWORD_TOKEN] ?: ""
        }
    }
}
