package com.falikiali.tmdbapp.presentation.detail

import com.falikiali.tmdbapp.domain.model.Media

sealed class DetailUiEvent {
    data class Refresh(val mediaType: String, val mediaId: Int): DetailUiEvent()
    data class LoadDetailMedia(val mediaType: String, val mediaId: Int): DetailUiEvent()
    data class OnAddOrRemoveBookmarkMedia(val value: Boolean, val media: Media): DetailUiEvent()
    data class LoadStatusBookmarkMedia(val mediaId: Int): DetailUiEvent()
}
