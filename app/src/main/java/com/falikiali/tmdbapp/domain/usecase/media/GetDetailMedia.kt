package com.falikiali.tmdbapp.domain.usecase.media

import com.falikiali.tmdbapp.domain.model.MediaDetail
import com.falikiali.tmdbapp.domain.repository.MediaRepository
import com.falikiali.tmdbapp.helper.ResultState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDetailMedia @Inject constructor(private val mediaRepository: MediaRepository) {

    suspend operator fun invoke(mediaType: String, mediaId: Int): Flow<ResultState<MediaDetail>> {
        return mediaRepository.getDetailMedia(mediaType, mediaId)
    }

}