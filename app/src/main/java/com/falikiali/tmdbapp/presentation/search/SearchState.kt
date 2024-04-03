package com.falikiali.tmdbapp.presentation.search

import androidx.paging.PagingData
import com.falikiali.tmdbapp.domain.model.Media
import kotlinx.coroutines.flow.Flow

data class SearchState(
    val searchQuery: String = "",
    val searchList: Flow<PagingData<Media>>? = null
)
