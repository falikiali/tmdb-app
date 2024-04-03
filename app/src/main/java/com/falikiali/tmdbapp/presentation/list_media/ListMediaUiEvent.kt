package com.falikiali.tmdbapp.presentation.list_media

sealed class ListMediaUiEvent {
    data class LoadListMedia(val mediaType: String, val mediaCategory: String): ListMediaUiEvent()
}
