package com.test.stunzilla.utils

import android.content.Context
import android.content.SharedPreferences

class PreferenceManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences(ConstVal.PREFS_NAME, Context.MODE_PRIVATE)

    fun isOnboardingCompleted(): Boolean {
        return prefs.getBoolean(ConstVal.KEY_ONBOARDING_SCREEN, false)
    }

    fun setOnboardingCompleted(completed: Boolean) {
        prefs.edit().putBoolean(ConstVal.KEY_ONBOARDING_SCREEN, completed).apply()
    }

    fun getToken(): String? {
        return prefs.getString(ConstVal.KEY_TOKEN, null)
    }

    fun setToken(token: String) {
        prefs.edit().putString(ConstVal.KEY_TOKEN, token).apply()
    }
}