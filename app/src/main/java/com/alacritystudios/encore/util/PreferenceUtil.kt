package com.alacritystudios.encore.util

import android.content.Context
import android.preference.PreferenceManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferenceUtil @Inject
constructor(var context: Context) {

    fun getThemePreference(): Boolean {
        var value = false
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        if (preferences != null) {
            value = preferences.getBoolean(PREFERENCE_DARK_MODE, false)
        }
        return value
    }

    enum class ThemeEnum {
        WHITE,
        BLACK
    }

    companion object {

        var PREFERENCE_DARK_MODE = "pref_dark_mode"
    }
}
