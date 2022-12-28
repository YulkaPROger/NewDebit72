package com.example.debit72.android.presenter.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DesignServices
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Mode
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.debit72.android.R

sealed class NavigationItem(var route: String, @DrawableRes var icon: Int, @StringRes var title: Int) {
    object Main : NavigationItem("main", R.drawable.main, R.string.main)
    object Service : NavigationItem("service", R.drawable.service, R.string.service)
    object More : NavigationItem("other", R.drawable.more, R.string.other)
}