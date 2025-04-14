package com.smtersoyoglu.movieapp.presentation.register

data class RegisterUiState(
    val isLoading: Boolean = false,
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val error: String? = null,
    val isRegistered: Boolean = false
)
