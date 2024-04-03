package com.falikiali.tmdbapp.presentation.home

import com.falikiali.tmdbapp.domain.model.Media

data class HomeState(
    val isLoading: Boolean = false,
    val isError: String? = null,
    val isRefresh: Boolean = false,
    val media: List<List<Media>>? = null
)
