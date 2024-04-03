package com.falikiali.tmdbapp.presentation.settings

sealed class SettingsUiEvent {
    data class OnDarkThemeClicked(val value: Boolean): SettingsUiEvent()
}