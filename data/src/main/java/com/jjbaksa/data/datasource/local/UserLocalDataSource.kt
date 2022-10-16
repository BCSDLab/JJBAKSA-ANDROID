package com.jjbaksa.data.datasource.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.jjbaksa.data.database.PreferenceKeys
import com.jjbaksa.data.database.UserDao
import com.jjbaksa.data.database.userDataStore
import com.jjbaksa.data.datasource.UserDataSource
import com.jjbaksa.data.model.user.LoginResp
import com.jjbaksa.domain.resp.user.LoginReq
import com.jjbaksa.domain.resp.user.SignUpReq
import com.jjbaksa.domain.resp.user.SignUpResp
import dagger.hilt.android.qualifiers.ApplicationContext
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

    override suspend fun checkAccountAvailable(account: String): Response<String> {
        TODO("Not yet implemented")
    }

    override suspend fun postLogin(loginReq: LoginReq): Response<LoginResp>? {
        return null
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
}
