package com.falikiali.tmdbapp.presentation.detail

import com.falikiali.tmdbapp.domain.model.MediaDetail

data class DetailState(
    val isLoading: Boolean = false,
    val isError: String? = null,
    val isBookmark: Boolean? = null,
    val detailMedia: MediaDetail? = null
)
