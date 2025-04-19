package com.smtersoyoglu.movieapp.presentation.register

data class RegisterUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val isRegistered: Boolean = false
)
