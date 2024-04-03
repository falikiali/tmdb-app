package com.falikiali.tmdbapp.domain.repository

import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {

    suspend fun setDarkThemePreferences(value: Boolean)
    suspend fun getDarkThemePreferences(): Flow<Boolean>

}