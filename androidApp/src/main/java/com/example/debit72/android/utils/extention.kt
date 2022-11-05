package com.example.debit72.android.utils

import android.content.res.Resources
import java.text.SimpleDateFormat
import java.util.*

val Int.inDp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()

val Int.inPx: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

fun Date.toNormalDate(format: String, locale: Locale = Locale.getDefault()): String {
    val formatter = SimpleDateFormat(format, locale)
    return formatter.format(this)
}

fun getCurrentDateTime(): Date {
    return Calendar.getInstance().time
}
