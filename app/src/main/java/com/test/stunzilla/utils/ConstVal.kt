package com.test.stunzilla.utils

import com.test.stunzilla.di.preferenceModule

object ConstVal {

    //Shared Preferences
    const val PREFS_NAME = "pestsentry.pref"
    const val KEY_TOKEN = "key.token"
    const val KEY_ONBOARDING_SCREEN = "key.onboarding_screen"

    const val SPLASH_SCREEN_DURATION = 3

    //Injections Modules
    val koinModules = listOf(preferenceModule)

}