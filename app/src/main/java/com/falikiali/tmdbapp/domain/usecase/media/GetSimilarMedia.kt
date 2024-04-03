package com.falikiali.tmdbapp.domain.usecase.media

import androidx.paging.PagingData
import com.falikiali.tmdbapp.domain.model.Media
import com.falikiali.tmdbapp.domain.repository.MediaRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSimilarMedia @Inject constructor(private val mediaRepository: MediaRepository) {

    suspend operator fun invoke(mediaType: String, mediaId: Int): Flow<PagingData<Media>> {
        return mediaRepository.getSimilarMedia(mediaType = mediaType, mediaId = mediaId)
    }

}