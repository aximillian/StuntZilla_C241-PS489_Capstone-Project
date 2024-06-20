package com.test.stunzilla.utils

import com.test.stunzilla.di.preferenceModule

object ConstVal {

    //Splash Screen
    const val SPLASH_SCREEN_DURATION = 3

    //Shared Preferences
    const val PREFS_NAME = "stunzilla_pref"
    const val KEY_TOKEN = "user_token"
    const val KEY_ONBOARDING_SCREEN = "key.onboarding_screen"


    //Injections Modules
    val koinModules = listOf(preferenceModule)
}