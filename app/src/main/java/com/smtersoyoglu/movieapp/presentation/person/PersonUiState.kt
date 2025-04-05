package com.smtersoyoglu.movieapp.presentation.person

import com.smtersoyoglu.movieapp.domain.model.PersonDetails
import com.smtersoyoglu.movieapp.domain.model.PersonExternalIds
import com.smtersoyoglu.movieapp.domain.model.PersonImage
import com.smtersoyoglu.movieapp.domain.model.PersonMovieCredits

data class PersonUiState (
    val isLoading: Boolean = false,
    val personDetails: PersonDetails? = null,
    val personMovieCredits: PersonMovieCredits? = null,
    val personExternalIds: PersonExternalIds? = null,
    val personImages: List<PersonImage> = emptyList(),
    val error: String? = null,
    val isBiographyExpanded: Boolean = false
)