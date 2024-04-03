package com.falikiali.tmdbapp.presentation.list_media

import androidx.paging.PagingData
import com.falikiali.tmdbapp.domain.model.Media
import kotlinx.coroutines.flow.Flow

data class ListMediaState(
    val listMedia: Flow<PagingData<Media>>? = null
)
