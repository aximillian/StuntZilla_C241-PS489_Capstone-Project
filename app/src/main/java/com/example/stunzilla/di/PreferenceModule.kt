package com.example.stunzilla.di

import com.example.stunzilla.utils.PreferenceManager
import org.koin.dsl.module

val preferenceModule = module {
    single { PreferenceManager(get()) }
}