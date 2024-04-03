package com.falikiali.tmdbapp.presentation.similar_media

import androidx.paging.PagingData
import com.falikiali.tmdbapp.domain.model.Media
import kotlinx.coroutines.flow.Flow

data class SimilarMediaState(
    val similarMedia: Flow<PagingData<Media>>? = null
)
