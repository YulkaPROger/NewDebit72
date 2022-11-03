package com.example.debit72.android.data_store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class UserSettings(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("UserSettings")
        val DATE_UPDATE_IP = stringSetPreferencesKey("date_update_ip")
        val DARK_THEME = booleanPreferencesKey("dark_theme")
    }

    val getDateUpdateIp: Flow<String> = context.dataStore.data
        .map { pref ->
            pref[DATE_UPDATE_IP].toString()
        }

    suspend fun setDateUpdateIp(date: String) {
        context.dataStore.edit { pref ->
            pref[DATE_UPDATE_IP] = setOf(date)
        }
    }

    val isDarkTheme: Flow<Boolean> = context.dataStore.data
        .map { pref ->
            pref[DARK_THEME] ?: true
        }

    suspend fun setDarkTheme(darkTheme: Boolean) {
        context.dataStore.edit { pref ->
            pref[DARK_THEME] = darkTheme
        }
    }
}