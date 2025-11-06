package com.smtersoyoglu.movieapp.presentation.seeall

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.smtersoyoglu.movieapp.domain.model.movie.Movie
import com.smtersoyoglu.movieapp.domain.usecase.home.GetNowPlayingMoviesUseCase
import com.smtersoyoglu.movieapp.domain.usecase.home.GetPopularMoviesUseCase
import com.smtersoyoglu.movieapp.domain.usecase.home.GetTopRatedMoviesUseCase
import com.smtersoyoglu.movieapp.domain.usecase.home.GetUpcomingMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

@HiltViewModel
class SeeAllViewModel @Inject constructor(
    private val getNowPlaying: GetNowPlayingMoviesUseCase,
    private val getPopular: GetPopularMoviesUseCase,
    private val getTopRated: GetTopRatedMoviesUseCase,
    private val getUpcoming: GetUpcomingMoviesUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        SeeAllUiState(
            title = savedStateHandle["title"] ?: "",
            type = savedStateHandle["type"] ?: "popular"
        )
    )
    val uiState: StateFlow<SeeAllUiState> = _uiState.asStateFlow()

    val paging: Flow<PagingData<Movie>> =
        when (_uiState.value.type) {
            "now_playing" -> getNowPlaying()
            "popular" -> getPopular()
            "top_rated" -> getTopRated()
            "upcoming" -> getUpcoming()
            else -> getPopular()
        }.cachedIn(viewModelScope)
}
