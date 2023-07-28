package com.jjbaksa.data.datasource.local

import android.content.Context
import com.jjbaksa.data.database.PreferenceKeys
import com.jjbaksa.data.database.UserDao
import com.jjbaksa.data.database.userDataStore
import com.jjbaksa.data.datasource.HomeDataSource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class HomeLocalDataSource @Inject constructor(
    @ApplicationContext context: Context,
    private val userDao: UserDao
) : HomeDataSource {
    private val dataStore = context.userDataStore
    override fun getUserAutoLogin(): Boolean {
        return runBlocking {
            dataStore.data.first()[PreferenceKeys.AUTO_LOGIN] ?: false
        }
    }

    override fun getUserAccount(): String {
        return runBlocking {
            dataStore.data.first()[PreferenceKeys.ACCOUNT] ?: ""
        }
    }

    override fun getUserNickname(): String {
        return runBlocking {
            dataStore.data.first()[PreferenceKeys.NICKNAME] ?: ""
        }
    }

    override fun getUserFollowers(): Int {
        return runBlocking {
            dataStore.data.first()[PreferenceKeys.FOLLOWERS] ?: 0
        }
    }

    override fun getUserProfileImage(): String {
        return runBlocking {
            dataStore.data.first()[PreferenceKeys.IMAGE] ?: ""
        }
    }

    override fun getUserToken(): String {
        return runBlocking {
            dataStore.data.first()[PreferenceKeys.ACCESS_TOKEN] ?: ""
        }
    }
}
