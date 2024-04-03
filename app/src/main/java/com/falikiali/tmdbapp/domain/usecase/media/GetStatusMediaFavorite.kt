package com.falikiali.tmdbapp.domain.usecase.media

import com.falikiali.tmdbapp.domain.repository.MediaRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetStatusMediaFavorite @Inject constructor(private val mediaRepository: MediaRepository) {

    suspend operator fun invoke(mediaId: Int): Flow<Boolean> {
        return mediaRepository.statusBookmarkMedia(mediaId)
    }

}