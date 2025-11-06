package com.smtersoyoglu.movieapp.presentation.home

import com.smtersoyoglu.movieapp.domain.model.movie.Movie

data class HomeUiState(
    val isLoading: Boolean = false,
    val trendingMovieList: List<Movie> = emptyList(),
    val error: String? = null,
    val isNoInternet: Boolean = false,
)