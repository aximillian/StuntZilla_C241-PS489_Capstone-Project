package com.test.stunzilla.di

import com.test.stunzilla.utils.PreferenceManager
import org.koin.dsl.module

val preferenceModule = module {
    single { PreferenceManager(get()) }
}