package com.smtersoyoglu.movieapp.domain.repository

import com.smtersoyoglu.movieapp.common.Resource
import com.smtersoyoglu.movieapp.domain.model.UserProfile

interface AuthRepository {

    suspend fun signIn(email: String, password: String): Resource<String>
    suspend fun signUp(email: String, password: String, fullName: String): Resource<String>
    suspend fun getUserProfile(): Resource<UserProfile>
    suspend fun sendPasswordResetEmail(email: String): Resource<String>
    suspend fun signOut(): Resource<String>
    suspend fun isUserSignedIn(): Boolean
    suspend fun getCurrentUser(): String?

}