package com.example.stunzilla.utils

import android.content.Context
import android.content.SharedPreferences

class PreferenceManager(context: Context) {
    private var prefs: SharedPreferences =
        context.getSharedPreferences(ConstVal.PREFS_NAME, Context.MODE_PRIVATE)
    private val editor = prefs.edit()

    val getToken = prefs.getString(ConstVal.KEY_TOKEN, "")
    val getOnBoardingScreen = prefs.getBoolean(ConstVal.KEY_ONBOARDING_SCREEN, false)

    fun setOnboardingScreenPreference() {
        editor.putBoolean(ConstVal.KEY_ONBOARDING_SCREEN, true)
        editor.apply()
    }
}