package com.falikiali.tmdbapp.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.falikiali.tmdbapp.domain.usecase.MediaUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val mediaUseCase: MediaUseCase): ViewModel() {

    private val _searchMediaState = MutableStateFlow(SearchState())
    val searchMediaState = _searchMediaState.asStateFlow()

    init {
        searchMedia()
    }

    fun onEvent(event: SearchUiEvent) {
        when (event) {
            is SearchUiEvent.OnSearchQueryChange -> {
                _searchMediaState.update { state ->
                    state.copy(searchQuery = event.query)
                }
            }

            is SearchUiEvent.OnSearchMedia -> {
                searchMedia()
            }
        }
    }

    @OptIn(FlowPreview::class)
    private fun searchMedia() {
        viewModelScope.launch {
            val result = mediaUseCase.searchMedia.invoke(searchMediaState.value.searchQuery)
                .debounce(500L)

            _searchMediaState.update { state ->
                state.copy(searchList = result)
            }
        }
    }

}