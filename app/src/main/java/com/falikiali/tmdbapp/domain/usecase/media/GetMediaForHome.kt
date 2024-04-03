package com.falikiali.tmdbapp.domain.usecase.media

import com.falikiali.tmdbapp.domain.model.Media
import com.falikiali.tmdbapp.domain.repository.MediaRepository
import com.falikiali.tmdbapp.helper.ResultState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMediaForHome @Inject constructor(private val mediaRepository: MediaRepository) {

    suspend operator fun invoke(): Flow<ResultState<List<List<Media>>>> {
        return mediaRepository.getMediaForHome()
    }

}