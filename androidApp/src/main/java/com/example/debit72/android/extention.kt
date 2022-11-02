package com.example.debit72.android

import android.content.res.Resources

val Int.inDp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()

val Int.inPx: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()
