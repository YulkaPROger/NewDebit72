package com.example.debit72.android

import androidx.lifecycle.ViewModel
import com.example.debit72.datastore.UserSettingsRepository
import java.util.prefs.AbstractPreferences


class DebitViewModel(
    val userPreferences: UserSettingsRepository
) : ViewModel() {
}