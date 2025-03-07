package com.apui.interactivetaskmanager

import android.app.Application
import com.apui.interactivetaskmanager.di.dataStoreModule
import com.apui.interactivetaskmanager.di.databaseModule
import com.apui.interactivetaskmanager.di.topBarViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(topBarViewModelModule, databaseModule, dataStoreModule)
        }
    }
}