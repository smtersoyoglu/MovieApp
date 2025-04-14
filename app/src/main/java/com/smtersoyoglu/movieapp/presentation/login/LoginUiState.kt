package com.smtersoyoglu.movieapp.presentation.login

data class LoginUiState(
    val isLoading: Boolean = false,
    val email: String = "",
    val password: String = "",
    val error: String? = null,
    val isLoggedIn: Boolean = false
)
