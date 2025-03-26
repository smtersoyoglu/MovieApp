package com.smtersoyoglu.movieapp.presentation.detail

import com.smtersoyoglu.movieapp.domain.model.MovieCredits
import com.smtersoyoglu.movieapp.domain.model.MovieDetails
import com.smtersoyoglu.movieapp.domain.model.MovieVideos

data class DetailUiState(
    val isLoading: Boolean = false,
    val movieDetails: MovieDetails? = null,
    val movieCredits: MovieCredits? = null,
    val movieVideos: MovieVideos? = null,
    val error: String? = null,
)