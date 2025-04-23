package com.smtersoyoglu.movieapp.domain.model

import com.google.firebase.firestore.PropertyName

data class UserProfile(
    @PropertyName("userId") val userId: String = "",
    @PropertyName("fullName") val fullName: String = "",
    @PropertyName("email") val email: String = ""
)