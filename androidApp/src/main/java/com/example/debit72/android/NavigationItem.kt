package com.example.debit72.android

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

sealed class NavigationItem(var route: String, @DrawableRes var icon: Int, @StringRes var title: Int) {
    object Objects : NavigationItem("objects", R.drawable.ic_objects, R.string.ip)
    object Services : NavigationItem("services", R.drawable.ic_services, R.string.property)
    object Materials : NavigationItem("home", R.drawable.ic_materials, R.string.avto)
    object Profile : NavigationItem("profile", R.drawable.ic_profile, R.string.other)

}