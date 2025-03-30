package com.smtersoyoglu.movieapp.presentation.person

import com.smtersoyoglu.movieapp.domain.model.PersonDetails

data class PersonUiState (
    val isLoading: Boolean = false,
    val personDetails: PersonDetails? = null,
    val error: String? = null,
)