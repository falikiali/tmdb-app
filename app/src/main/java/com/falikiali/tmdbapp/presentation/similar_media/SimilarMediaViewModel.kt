package com.falikiali.tmdbapp.presentation.similar_media

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.falikiali.tmdbapp.domain.usecase.MediaUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SimilarMediaViewModel @Inject constructor(private val mediaUseCase: MediaUseCase): ViewModel() {

    private val _similarMedia = MutableStateFlow(SimilarMediaState())
    val similarMedia = _similarMedia.asStateFlow()

    fun onEvent(event: SimilarMediaUiEvent) {
        when (event) {
            is SimilarMediaUiEvent.LoadSimilarMedia -> {
                getSimilarMedia(event.mediaType, event.mediaId)
            }
        }
    }

    private fun getSimilarMedia(mediaType: String, mediaId: Int) {
        viewModelScope.launch {
            val result = mediaUseCase.getSimilarMedia.invoke(mediaType = mediaType, mediaId = mediaId)

            _similarMedia.update { state ->
                state.copy(similarMedia = result)
            }
        }
    }

}