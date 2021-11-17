package com.example.phototester.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class AppPhoto : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            printLogger()
            androidContext(applicationContext)
            modules(listOf(application, fragments))
        }

        Timber.plant(Timber.DebugTree())
    }

}