package com.example.debit72.android

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DesignServices
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Mode
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavigationItem(var route: String, var icon: ImageVector, @StringRes var title: Int) {
    object Main : NavigationItem("main", Icons.Rounded.Home, R.string.main)
    object Service : NavigationItem("service", Icons.Rounded.Mode, R.string.service)
    object Profile : NavigationItem("other", Icons.Rounded.DesignServices, R.string.other)
}