package com.falikiali.tmdbapp.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.falikiali.tmdbapp.domain.usecase.MediaUseCase
import com.falikiali.tmdbapp.helper.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val mediaUseCase: MediaUseCase): ViewModel() {

    private val _detailState = MutableStateFlow(DetailState())
    val detailState = _detailState.asStateFlow()

    fun onEvent(event: DetailUiEvent) {
        when (event) {
            is DetailUiEvent.Refresh -> {
                getDetailMedia(event.mediaType, event.mediaId)
                getStatusBookmarkMedia(event.mediaId)
            }
            is DetailUiEvent.LoadDetailMedia -> {
                getDetailMedia(event.mediaType, event.mediaId)
            }
            is DetailUiEvent.OnAddOrRemoveBookmarkMedia -> {
                if (event.value) {
                    viewModelScope.launch {
                        mediaUseCase.removeBookmarkMedia.invoke(event.media)
                    }
                } else {
                    viewModelScope.launch {
                        mediaUseCase.addBookmarkMedia.invoke(event.media)
                    }
                }
            }
            is DetailUiEvent.LoadStatusBookmarkMedia -> {
                getStatusBookmarkMedia(event.mediaId)
            }
        }
    }

    private fun getDetailMedia(mediaType: String, mediaId: Int) {
        viewModelScope.launch {
            mediaUseCase.getDetailMedia.invoke(mediaType, mediaId).collect {
                when (it) {
                    is ResultState.Loading -> {
                        _detailState.update { state ->
                            state.copy(
                                detailMedia = null,
                                isBookmark = null,
                                isError = null,
                                isLoading = true
                            )
                        }
                    }
                    is ResultState.Success -> {
                        _detailState.update { state ->
                            state.copy(
                                isLoading = false,
                                detailMedia = it.data
                            )
                        }
                    }
                    is ResultState.Failed -> {
                        _detailState.update { state ->
                            state.copy(
                                isLoading = false,
                                isError = it.error
                            )
                        }
                    }
                }
            }
        }
    }

    private fun getStatusBookmarkMedia(mediaId: Int) {
        viewModelScope.launch {
            mediaUseCase.getStatusMediaBookmark.invoke(mediaId).collect {
                _detailState.update { state ->
                    state.copy(isBookmark = it)
                }
            }
        }
    }

}