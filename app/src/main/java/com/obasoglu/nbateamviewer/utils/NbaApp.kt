package com.obasoglu.nbateamviewer.utils

import android.app.Application
import com.obasoglu.nbateamviewer.BuildConfig
import com.obasoglu.nbateamviewer.di.apiModule
import com.obasoglu.nbateamviewer.di.networkModule
import com.obasoglu.nbateamviewer.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

/**
 * [NbaApp] is an extension of Application class
 * Loads Koin and Timber on application start
 */
class NbaApp : Application() {

    override fun onCreate() {
        super.onCreate()

        // Initialize Koin
        startKoin {
            // use AndroidLogger as Koin Logger
            androidLogger(Level.DEBUG)
            // use the Android context
            androidContext(this@NbaApp)
            // module list
            modules(listOf(viewModelModule, apiModule, networkModule))
        }

        // Initialize Timber in Debug mode only
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

}