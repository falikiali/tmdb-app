package com.falikiali.tmdbapp.domain.usecase.preferences

import com.falikiali.tmdbapp.domain.repository.PreferencesRepository
import javax.inject.Inject

class SetDarkThemePreferences @Inject constructor(private val preferencesRepository: PreferencesRepository) {

    suspend operator fun invoke(value: Boolean) {
        return preferencesRepository.setDarkThemePreferences(value)
    }

}