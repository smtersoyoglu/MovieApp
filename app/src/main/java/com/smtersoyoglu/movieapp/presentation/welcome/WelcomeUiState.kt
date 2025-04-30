package com.smtersoyoglu.movieapp.presentation.welcome

data class WelcomeUiState(
    val isLoading: Boolean = false,
    val isUserSignedIn: Boolean? = null,
    val error: String? = null
)
