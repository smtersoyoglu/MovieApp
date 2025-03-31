package com.smtersoyoglu.movieapp.presentation.person

import com.smtersoyoglu.movieapp.domain.model.PersonDetails
import com.smtersoyoglu.movieapp.domain.model.PersonMovieCredits

data class PersonUiState (
    val isLoading: Boolean = false,
    val personDetails: PersonDetails? = null,
    val personMovieCredits: PersonMovieCredits? = null,
    val error: String? = null,
)