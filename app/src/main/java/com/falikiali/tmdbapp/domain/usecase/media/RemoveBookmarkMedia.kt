package com.falikiali.tmdbapp.domain.usecase.media

import com.falikiali.tmdbapp.domain.model.Media
import com.falikiali.tmdbapp.domain.repository.MediaRepository
import javax.inject.Inject

class RemoveBookmarkMedia @Inject constructor(private val mediaRepository: MediaRepository) {

    suspend operator fun invoke(media: Media) {
        mediaRepository.removeBookmarkMedia(media)
    }

}