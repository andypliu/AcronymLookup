package com.albertsons.acronyms

import android.app.Application
import timber.log.Timber

class AcronymApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())  // setup Timber for logging
    }
}