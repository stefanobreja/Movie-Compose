package com.obi.moviecompose

import android.app.Application
import com.obi.moviecompose.di.appModule
import com.obi.moviecompose.di.dataModule
import com.obi.moviecompose.di.domainModule
import com.obi.moviecompose.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.compose.KoinContext
import org.koin.core.context.startKoin

class MovieComposeApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MovieComposeApplication)
            modules(appModule, dataModule, domainModule, viewModelModule)
        }
    }
}