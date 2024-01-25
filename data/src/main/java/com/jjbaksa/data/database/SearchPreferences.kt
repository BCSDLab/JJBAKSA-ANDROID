package com.jjbaksa.data.database

import android.content.Context
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

val Context.searchDataStore by preferencesDataStore(
    name = "SearchPreferences"
)
object SearchPreferenceKeys {
    val SEARCH_HISTORY = stringPreferencesKey("SEARCH_HISTORY")
}
