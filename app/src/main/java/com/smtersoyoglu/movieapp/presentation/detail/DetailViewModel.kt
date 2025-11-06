package com.smtersoyoglu.movieapp.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.smtersoyoglu.movieapp.common.Resource
import com.smtersoyoglu.movieapp.domain.usecase.detail.GetMovieCreditsUseCase
import com.smtersoyoglu.movieapp.domain.usecase.detail.GetMovieDetailsUseCase
import com.smtersoyoglu.movieapp.domain.usecase.detail.GetMovieImagesUseCase
import com.smtersoyoglu.movieapp.domain.usecase.detail.GetMovieVideosUseCase
import com.smtersoyoglu.movieapp.domain.usecase.detail.GetMovieWatchProvidersUseCase
import com.smtersoyoglu.movieapp.domain.usecase.detail.GetSimilarMoviesUseCase
import com.smtersoyoglu.movieapp.domain.usecase.detail.IsFavoriteUseCase
import com.smtersoyoglu.movieapp.domain.usecase.favorite.ToggleFavoriteUseCase
import com.smtersoyoglu.movieapp.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getMovieCreditsUseCase: GetMovieCreditsUseCase,
    private val getMovieVideosUseCase: GetMovieVideosUseCase,
    private val getSimilarMoviesUseCase: GetSimilarMoviesUseCase,
    private val getMovieImagesUseCase: GetMovieImagesUseCase,
    private val getMovieWatchProvidersUseCase: GetMovieWatchProvidersUseCase,
    private val isFavoriteUseCase: IsFavoriteUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    private val args = savedStateHandle.toRoute<Screen.Detail>()

    init {
        getMovieDetails(args.movieId)
        getMovieCredits(args.movieId)
        getMovieVideos(args.movieId)
        getMovieWatchProviders(args.movieId)
        getSimilarMovies(args.movieId)
        getMovieImages(args.movieId)
        checkFavoriteStatus(args.movieId)
    }

    private fun getMovieDetails(movieId: Int) {
        viewModelScope.launch {
            updateState { copy(isLoading = true) }
            when (val result = getMovieDetailsUseCase(movieId)) {
                is Resource.Success -> {
                    updateState { copy(isLoading = false, movieDetails = result.data, error = null) }
                }
                is Resource.Error -> {
                    updateState { copy(isLoading = false, error = result.message) }
                }
            }
        }
    }

    private fun getMovieCredits(movieId: Int) {
        viewModelScope.launch {
            updateState { copy(isLoading = true) }
            when (val result = getMovieCreditsUseCase(movieId)) {
                is Resource.Success -> {
                    updateState { copy(isLoading = false, movieCredits = result.data, error = null) }
                }
                is Resource.Error -> {
                    updateState { copy(isLoading = false, error = result.message) }
                }
            }
        }

    }

    private fun getMovieVideos(movieId: Int) {
        viewModelScope.launch {
            updateState { copy(isLoading = true) }
            when (val result = getMovieVideosUseCase(movieId)) {
                is Resource.Success -> {
                    updateState { copy(isLoading = false, movieVideos = result.data, error = null) }
                }
                is Resource.Error -> {
                    updateState { copy(isLoading = false, error = result.message) }
                }
            }
        }

    }

    private fun getMovieWatchProviders(movieId: Int) {
        viewModelScope.launch {
            updateState { copy(isLoading = true) }
            when (val result = getMovieWatchProvidersUseCase(movieId)) {
                is Resource.Success -> {
                    updateState { copy(isLoading = false, movieWatchProviders = result.data, error = null) }
                }
                is Resource.Error -> {
                    updateState { copy(isLoading = false, error = result.message) }
                }
            }
        }
    }

    private fun getSimilarMovies(movieId: Int) {
        viewModelScope.launch {
            updateState { copy(isLoading = true) }
            when (val result = getSimilarMoviesUseCase(movieId)) {
                is Resource.Success -> {
                    updateState { copy(isLoading = false, similarMovies = result.data ?: emptyList(), error = null) }
                }
                is Resource.Error -> {
                    updateState { copy(isLoading = false, error = result.message) }
                }
            }
        }
    }

    private fun getMovieImages(movieId: Int) {
        viewModelScope.launch {
            updateState { copy(isLoading = true) }
            when (val result = getMovieImagesUseCase(movieId)) {
                is Resource.Success -> {
                    updateState { copy(isLoading = false, movieImages = result.data, error = null) }
                }
                is Resource.Error -> {
                    updateState { copy(isLoading = false, error = result.message) }
                }
            }
        }
    }

    fun selectTab(tabIndex: Int) {
        _uiState.update { it.copy(selectedTab = tabIndex) }
    }

    private fun checkFavoriteStatus(movieId: Int) {
        viewModelScope.launch {
            isFavoriteUseCase(movieId).collect { isFavorite ->
                updateState { copy(isFavorite = isFavorite) }
            }
        }
    }

    fun toggleFavorite() {
        val details = uiState.value.movieDetails ?: return
        viewModelScope.launch {
            when (val result = toggleFavoriteUseCase(details)) {
                is Resource.Success -> {
                    updateState {
                        copy(
                            isFavorite = result.data ?: false,
                            message = if (result.data == true) "Added to favorites" else "Removed from favorites"
                        )
                    }
                }
                is Resource.Error -> {
                    updateState { copy(error = result.message) }
                }
            }
        }
    }

    fun clearMessage() {
        updateState { copy(message = null) }
    }

    private fun updateState(block: DetailUiState.() -> DetailUiState) {
        _uiState.update(block)
    }
}