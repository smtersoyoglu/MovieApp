package com.smtersoyoglu.movieapp.presentation.detail

import com.smtersoyoglu.movieapp.domain.model.movie.Movie
import com.smtersoyoglu.movieapp.domain.model.movie.MovieCredits
import com.smtersoyoglu.movieapp.domain.model.movie.MovieDetails
import com.smtersoyoglu.movieapp.domain.model.movie.MovieImages
import com.smtersoyoglu.movieapp.domain.model.movie.MovieVideos

data class DetailUiState(
    val isLoading: Boolean = false,
    val movieDetails: MovieDetails? = null,
    val movieCredits: MovieCredits? = null,
    val movieVideos: MovieVideos? = null,
    val similarMovies: List<Movie> = emptyList(),
    val movieImages: MovieImages? = null,
    val error: String? = null,
    val selectedTab: Int = 0,
    val isFavorite: Boolean = false,
    val message: String? = null
)