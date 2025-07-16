package com.smtersoyoglu.movieapp.presentation.person

import com.smtersoyoglu.movieapp.domain.model.person.PersonDetails
import com.smtersoyoglu.movieapp.domain.model.person.PersonExternalIds
import com.smtersoyoglu.movieapp.domain.model.person.PersonImage
import com.smtersoyoglu.movieapp.domain.model.person.PersonMovieCredits

data class PersonUiState (
    val isLoading: Boolean = false,
    val personDetails: PersonDetails? = null,
    val personMovieCredits: PersonMovieCredits? = null,
    val personExternalIds: PersonExternalIds? = null,
    val personImages: List<PersonImage> = emptyList(),
    val error: String? = null,
    val isBiographyExpanded: Boolean = false
)