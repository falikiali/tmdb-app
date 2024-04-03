package com.falikiali.tmdbapp.presentation.list_media

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
class ListMediaViewModel @Inject constructor(private val mediaUseCase: MediaUseCase): ViewModel() {

    private val _listMediaState = MutableStateFlow(ListMediaState())
    val listMediaState = _listMediaState.asStateFlow()

    fun onEvent(event: ListMediaUiEvent) {
        when (event) {
            is ListMediaUiEvent.LoadListMedia -> {
                getListMedia(mediaType = event.mediaType, mediaCategory = event.mediaCategory)
            }
        }
    }

    private fun getListMedia(mediaType: String, mediaCategory: String) {
        viewModelScope.launch {
            val result = mediaUseCase.getListMedia.invoke(mediaType, mediaCategory)

            _listMediaState.update { state ->
                state.copy(
                    listMedia = result
                )
            }
        }
    }

}