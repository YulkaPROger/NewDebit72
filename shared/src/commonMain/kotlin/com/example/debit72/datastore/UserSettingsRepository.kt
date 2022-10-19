package com.example.debit72.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class UserSettingsRepository(
    private val dataStore: DataStore<Preferences>
) {
    companion object {
        const val DEFAULT_PHONE = ""
        const val DEFAULT_THEME = true
    }

    private val scope = CoroutineScope(Dispatchers.Default)

    private val userPhoneKey = stringPreferencesKey("user_phone")
    private val userLightThemeKey = booleanPreferencesKey("user_light_theme")

    val settings: Flow<AppDebitSettings> = dataStore.data.map {
        AppDebitSettings(
            it[userPhoneKey] ?: DEFAULT_PHONE,
            it[userLightThemeKey] ?: DEFAULT_THEME
        )
    }

    fun saveSettings(
        userPhone: String,
        userLightTheme: Boolean,
    ) {
        scope.launch {
            dataStore.edit {
                it[userPhoneKey] = userPhone
                it[userLightThemeKey] = userLightTheme
            }
        }
    }
}