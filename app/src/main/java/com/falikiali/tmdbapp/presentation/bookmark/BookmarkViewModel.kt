package com.falikiali.tmdbapp.presentation.bookmark

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
class BookmarkViewModel @Inject constructor(private val mediaUseCase: MediaUseCase): ViewModel() {

    private val _bookmarkState = MutableStateFlow(BookmarkState())
    val bookmarkState = _bookmarkState.asStateFlow()

    fun onEvent(event: BookmarkUiEvent) {
        when (event) {
            is BookmarkUiEvent.OnLoadBookmarkMedia -> {
                getBookmarkMedia()
            }

            is BookmarkUiEvent.OnSearchQueryChange -> {
                _bookmarkState.update { state ->
                    state.copy(query = event.query)
                }
            }
            is BookmarkUiEvent.OnFilterChange -> {
                _bookmarkState.update { state ->
                    state.copy(filter = event.filter)
                }
            }
        }
    }

    private fun getBookmarkMedia() {
        viewModelScope.launch {
            val result = mediaUseCase.getAllBookmarkMedia.invoke(type = bookmarkState.value.filter, query = bookmarkState.value.query)

            _bookmarkState.update { state ->
                state.copy(bookmarkMedia = result)
            }
        }
    }

}