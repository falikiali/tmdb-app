package com.falikiali.tmdbapp.presentation.home

sealed class HomeUiEvent {
    object Refresh: HomeUiEvent()
    object LoadMedia: HomeUiEvent()
}
