package com.falikiali.tmdbapp.presentation.bookmark

import androidx.paging.PagingData
import com.falikiali.tmdbapp.domain.model.Media
import kotlinx.coroutines.flow.Flow

data class BookmarkState(
    val query: String = "",
    val filter: String = "all",
    val bookmarkMedia: Flow<PagingData<Media>>? = null
)
