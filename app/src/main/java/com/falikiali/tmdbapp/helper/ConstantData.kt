package com.falikiali.tmdbapp.helper

import androidx.datastore.preferences.core.booleanPreferencesKey

object ConstantData {

    /**
     * Data store key
     */
    const val APP_PREFERENCES = "app_preferences"
    val IS_DARK_THEME = booleanPreferencesKey("is_dark_theme")

}