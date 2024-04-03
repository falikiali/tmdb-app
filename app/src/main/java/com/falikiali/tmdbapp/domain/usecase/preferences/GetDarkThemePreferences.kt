package com.falikiali.tmdbapp.domain.usecase.preferences

import com.falikiali.tmdbapp.domain.repository.PreferencesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDarkThemePreferences @Inject constructor(private val preferencesRepository: PreferencesRepository) {

    suspend operator fun invoke(): Flow<Boolean> {
        return preferencesRepository.getDarkThemePreferences()
    }

}