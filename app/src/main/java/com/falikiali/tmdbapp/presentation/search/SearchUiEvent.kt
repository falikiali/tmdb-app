package com.falikiali.tmdbapp.presentation.search

sealed class SearchUiEvent {
    data class OnSearchQueryChange(val query: String): SearchUiEvent()
    object OnSearchMedia: SearchUiEvent()
}
