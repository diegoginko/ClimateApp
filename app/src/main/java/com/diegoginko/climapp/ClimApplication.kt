package com.diegoginko.climapp

import android.app.Application
import timber.log.Timber

class ClimApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}