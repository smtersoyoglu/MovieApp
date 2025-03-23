package com.smtersoyoglu.movieapp.presentation.home

import com.smtersoyoglu.movieapp.domain.model.Movie

data class UiState(
    val isLoading: Boolean = false,
    val list: List<String> = emptyList(),
    val error: String? = null,
    val trendingMovieList: List<Movie> = emptyList(),
    val nowPlayingMovieList: List<Movie> = emptyList(),
    val popularMovieList: List<Movie> = emptyList(),
    val topRatedMovieList: List<Movie> = emptyList(),
    val upcomingMovieList: List<Movie> = emptyList(),
)