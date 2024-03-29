package com.jjbaksa.data.database

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

val Context.userDataStore by preferencesDataStore(
    name = "UserPreferences"
)
object PreferenceKeys {
    val ACCESS_TOKEN = stringPreferencesKey("ACEESS_TOKEN")
    val REFRESH_TOKEN = stringPreferencesKey("REFRESH_TOKEN")
    val ACCOUNT = stringPreferencesKey("ACCOUNT")
    val NICKNAME = stringPreferencesKey("NICKNAME")
    val FOLLOWERS = intPreferencesKey("FOLLOWERS")
    val REVIEWS = intPreferencesKey("REVIEWS")
    val IMAGE = stringPreferencesKey("IMAGE")
    var AUTO_LOGIN = booleanPreferencesKey("AUTO_LOGIN")
    val AUTH_PASSWORD_TOKEN = stringPreferencesKey("AUTH_PASSWORD_TOKEN")
}
