package com.smtersoyoglu.movieapp.presentation.profile

import com.smtersoyoglu.movieapp.domain.model.UserProfile

data class ProfileUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val userProfile: UserProfile? = null,
    val showSignOutDialog: Boolean = false,
    val isSignedOut: Boolean = false,
    val showAboutDialog: Boolean = false
)