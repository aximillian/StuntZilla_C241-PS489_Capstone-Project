package com.test.stunzilla.base

import android.app.Application
import com.test.stunzilla.BuildConfig
import com.test.stunzilla.utils.ConstVal
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level


class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@BaseApplication)
            modules (
                ConstVal.koinModules
            )
        }

        initializeLogging()
        initializeNetwork()
        initializeFirebase()
    }

    private fun initializeLogging() {
        if (BuildConfig.DEBUG) {
            // Setup logging untuk debugging
        }
    }

    private fun initializeNetwork() {
        // Setup Retrofit atau library networking lainnya
    }

    private fun initializeFirebase() {
        // Setup Firebase jika diperlukan
    }
}