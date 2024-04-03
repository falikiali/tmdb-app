package com.falikiali.tmdbapp.domain.usecase.media

import androidx.paging.PagingData
import com.falikiali.tmdbapp.domain.model.Media
import com.falikiali.tmdbapp.domain.repository.MediaRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAndSearchBookmarkMedia @Inject constructor(private val mediaRepository: MediaRepository) {

    suspend operator fun invoke(type: String, query: String): Flow<PagingData<Media>> {
        return mediaRepository.getAndSearchBookmarkMedia(type, query)
    }

}