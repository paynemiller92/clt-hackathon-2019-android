package com.figjam.hackathon2019

import android.app.Application
import com.facebook.stetho.Stetho
import com.figjam.hackathon2019.koin.repositoryModule
import com.figjam.hackathon2019.koin.viewModelModule
import org.koin.android.ext.android.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(viewModelModule, repositoryModule))
        Stetho.initializeWithDefaults(this)
    }
}