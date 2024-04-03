package com.falikiali.tmdbapp.domain.usecase

import com.falikiali.tmdbapp.domain.usecase.preferences.GetDarkThemePreferences
import com.falikiali.tmdbapp.domain.usecase.preferences.SetDarkThemePreferences
import javax.inject.Inject

data class PreferencesUseCase @Inject constructor(
    val getDarkThemePreferences: GetDarkThemePreferences,
    val setDarkThemePreferences: SetDarkThemePreferences
)
