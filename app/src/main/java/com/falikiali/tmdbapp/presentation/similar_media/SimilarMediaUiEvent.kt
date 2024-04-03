package com.falikiali.tmdbapp.presentation.similar_media

sealed class SimilarMediaUiEvent {
    data class LoadSimilarMedia(val mediaType: String, val mediaId: Int): SimilarMediaUiEvent()
}
