package com.alacritystudios.encore.util

import com.alacritystudios.encore.R
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ThemeUtil @Inject
constructor(internal var preferenceUtil: PreferenceUtil) {

//    fun setThemeFromPreferences(): Int {
//        return if (preferenceUtil.getThemePreference()) {
//            R.style.AppTheme_NIGHT
//        } else {
//            R.style.AppTheme_DAY
//        }
//    }
}