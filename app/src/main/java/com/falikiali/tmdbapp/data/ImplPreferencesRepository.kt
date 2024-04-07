package com.falikiali.tmdbapp.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.falikiali.tmdbapp.domain.repository.PreferencesRepository
import com.falikiali.tmdbapp.helper.ConstantData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ImplPreferencesRepository @Inject constructor(private val dataStore: DataStore<Preferences>):
    PreferencesRepository {
    override suspend fun setDarkThemePreferences(value: Boolean) {
        dataStore.edit { preferences ->
            preferences[ConstantData.IS_DARK_THEME] = value
        }
    }

    override suspend fun getDarkThemePreferences(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[ConstantData.IS_DARK_THEME] ?: false
        }
    }
}