package com.example.debit72.android.data_store

import android.content.Context
import androidx.compose.runtime.collectAsState
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map


class UserSettings(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("UserSettings")
        val API_KEY = stringPreferencesKey("api_key")
        val DARK_THEME = booleanPreferencesKey("dark_theme")
        val DATE_UPDATE_IP = stringPreferencesKey("date_update_ip")
        val COUNT_IP = stringPreferencesKey("count_ip")
        val DATE_UPDATE_SPR = stringPreferencesKey("date_update_spr")
        val COUNT_SPR = stringPreferencesKey("count_spr")
    }

    fun getString(name: Preferences.Key<String>): Flow<String> = context.dataStore.data
        .map { pref ->
            pref[name] ?: ""
        }

    suspend fun setString(value: String, name: Preferences.Key<String>) {
        context.dataStore.edit { pref ->
            pref[name] = value
        }
    }

    fun getBoolean(name: Preferences.Key<Boolean>): Flow<Boolean> = context.dataStore.data
        .map { pref ->
            pref[name] ?: true
        }

    suspend fun setBoolean(value: Boolean, name: Preferences.Key<Boolean>) {
        context.dataStore.edit { pref ->
            pref[name] = value
        }
    }

    fun getInt(name: Preferences.Key<Int>): Flow<Int> = context.dataStore.data
        .map { pref ->
            pref[name] ?: 0
        }

    suspend fun setInt(value: Int, name: Preferences.Key<Int>) {
        context.dataStore.edit { pref ->
            pref[name] = value
        }
    }
}