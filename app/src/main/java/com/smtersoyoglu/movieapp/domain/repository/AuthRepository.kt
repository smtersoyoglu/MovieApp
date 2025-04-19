package com.smtersoyoglu.movieapp.domain.repository

import com.smtersoyoglu.movieapp.common.Resource

interface AuthRepository {

    suspend fun signIn(email: String, password: String): Resource<String>
    suspend fun signUp(email: String, password: String): Resource<String>
    suspend fun sendPasswordResetEmail(email: String): Resource<String>
    suspend fun signOut() : Resource<String>
    suspend fun getCurrentUser(): String?

}