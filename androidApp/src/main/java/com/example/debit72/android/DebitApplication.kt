package com.example.debit72.android

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob


class DebitApplication : Application() {

    companion object {
        lateinit var application: DebitApplication
            private set
        const val FILE_PROVIDER = BuildConfig.APPLICATION_ID + ".folder.provider"
    }


    val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    override fun onCreate() {
        super.onCreate()

        application = this
    }
}