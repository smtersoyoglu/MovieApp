package com.smtersoyoglu.movieapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smtersoyoglu.movieapp.common.Resource
import com.smtersoyoglu.movieapp.domain.usecase.GetNowPlayingMoviesUseCase
import com.smtersoyoglu.movieapp.domain.usecase.GetPopularMoviesUseCase
import com.smtersoyoglu.movieapp.domain.usecase.GetTopRatedMoviesUseCase
import com.smtersoyoglu.movieapp.domain.usecase.GetTrendingMoviesUseCase
import com.smtersoyoglu.movieapp.domain.usecase.GetUpcomingMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTrendingMoviesUseCase: GetTrendingMoviesUseCase,
    private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase,
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        getTrendingMovies()
        getNowPlayingMovies()
        getPopularMovies()
        getTopRatedMovies()
        getUpcomingMovies()
    }

    private fun getTrendingMovies() {
        getTrendingMoviesUseCase()
            .onStart { updateUiState { copy(isLoading = true) } }
            .onCompletion { updateUiState { copy(isLoading = false) } }
            .onEach { resource ->
                when (resource) {
                    is Resource.Success -> { updateUiState { copy(trendingMovieList = resource.data ?: emptyList()) } }
                    is Resource.Error -> { updateUiState { copy(error = resource.message ?: "Unknown error") } }
                }
            }.launchIn(viewModelScope)
    }

    private fun getNowPlayingMovies() {
        getNowPlayingMoviesUseCase()
            .onStart { updateUiState { copy(isLoading = true) } }
            .onCompletion { updateUiState { copy(isLoading = false) } }
            .onEach { resource ->
                when (resource) {
                    is Resource.Success -> { updateUiState { copy(nowPlayingMovieList = resource.data ?: emptyList()) } }
                    is Resource.Error -> { updateUiState { copy(error = resource.message ?: "Unknown error") } }
                }
            }.launchIn(viewModelScope)
    }

    private fun getPopularMovies() {
        getPopularMoviesUseCase()
            .onStart { updateUiState { copy(isLoading = true) } }
            .onCompletion { updateUiState { copy(isLoading = false) } }
            .onEach { resource ->
                when (resource) {
                    is Resource.Success -> { updateUiState { copy(popularMovieList = resource.data ?: emptyList()) } }
                    is Resource.Error -> { updateUiState { copy(error = resource.message ?: "Unknown error") } }
                }
            }.launchIn(viewModelScope)
    }

    private fun getTopRatedMovies() {
        getTopRatedMoviesUseCase()
            .onStart { updateUiState { copy(isLoading = true) } }
            .onCompletion { updateUiState { copy(isLoading = false) } }
            .onEach { resource ->
                when (resource) {
                    is Resource.Success -> { updateUiState { copy(topRatedMovieList = resource.data ?: emptyList()) } }
                    is Resource.Error -> { updateUiState { copy(error = resource.message ?: "Unknown error") } }
                }
            }.launchIn(viewModelScope)
    }

    private fun getUpcomingMovies() {
        getUpcomingMoviesUseCase()
            .onStart { updateUiState { copy(isLoading = true) } }
            .onCompletion { updateUiState { copy(isLoading = false) } }
            .onEach { resource ->
                when (resource) {
                    is Resource.Success -> { updateUiState { copy(upcomingMovieList = resource.data ?: emptyList()) } }

                    is Resource.Error -> { updateUiState { copy(error = resource.message ?: "Unknown error") } }
                }
            }.launchIn(viewModelScope)
    }


    private fun updateUiState(block: UiState.() -> UiState) {
        _uiState.update(block)
    }
}
