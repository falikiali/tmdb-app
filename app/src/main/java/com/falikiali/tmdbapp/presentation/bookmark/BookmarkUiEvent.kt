package com.falikiali.tmdbapp.presentation.bookmark

sealed class BookmarkUiEvent {
    data class OnSearchQueryChange(val query: String): BookmarkUiEvent()
    data class OnFilterChange(val filter: String): BookmarkUiEvent()
    object OnLoadBookmarkMedia: BookmarkUiEvent()
}
