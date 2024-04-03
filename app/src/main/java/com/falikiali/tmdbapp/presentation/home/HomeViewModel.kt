package com.falikiali.tmdbapp.presentation.home

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
class HomeViewModel @Inject constructor(private val mediaUseCase: MediaUseCase): ViewModel() {

    private val _homeState = MutableStateFlow(HomeState())
    val homeState = _homeState.asStateFlow()

    fun onEvent(event: HomeUiEvent) {
        when (event) {
            is HomeUiEvent.LoadMedia -> {
                getMediaForHome()
            }
            is HomeUiEvent.Refresh -> {
                _homeState.update { state ->
                    state.copy(
                        isRefresh = true
                    )
                }
            }
        }
    }

    private fun getMediaForHome() {
        viewModelScope.launch {
            mediaUseCase.getMediaForHome.invoke().collect {
                when (it) {
                    is ResultState.Loading -> {
                        _homeState.update { state ->
                            state.copy(
                                isLoading = true,
                                isError = null,
                                media = null,
                                isRefresh = false
                            )
                        }
                    }

                    is ResultState.Failed -> {
                        _homeState.update { state ->
                            state.copy(
                                isLoading = false,
                                isError = it.error
                            )
                        }
                    }

                    is ResultState.Success -> {
                        _homeState.update { state ->
                            state.copy(
                                isLoading = false,
                                media = it.data
                            )
                        }
                    }
                }
            }
        }
    }

}