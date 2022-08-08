package com.example.debit72.android

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

sealed class NavigationItem(var route: String, @DrawableRes var icon: Int, @StringRes var title: Int) {
    object IP : NavigationItem("ip", R.drawable.ic_objects, R.string.ip)
    object Home : NavigationItem("avto", R.drawable.ic_materials, R.string.avto)
    object Property : NavigationItem("property", R.drawable.ic_services, R.string.property)
    object Profile : NavigationItem("other", R.drawable.ic_profile, R.string.other)

}