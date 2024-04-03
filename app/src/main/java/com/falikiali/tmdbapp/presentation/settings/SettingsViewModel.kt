package com.falikiali.tmdbapp.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.falikiali.tmdbapp.domain.usecase.PreferencesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val preferencesUseCase: PreferencesUseCase): ViewModel() {

    private val _settingsState = MutableStateFlow(SettingsState())
    val settingsState = _settingsState.asStateFlow()

    init {
        getDarkTheme()
    }

    fun onEvent(event: SettingsUiEvent) {
        when (event) {
            is SettingsUiEvent.OnDarkThemeClicked -> {
                setDarkTheme(event.value)
            }
        }
    }

    private fun setDarkTheme(value: Boolean) {
        viewModelScope.launch {
            preferencesUseCase.setDarkThemePreferences.invoke(value)
        }
    }

    private fun getDarkTheme() {
        viewModelScope.launch {
            preferencesUseCase.getDarkThemePreferences.invoke().collectLatest { value ->
                _settingsState.update { state ->
                    state.copy(isDarkTheme = value)
                }
            }
        }
    }

}