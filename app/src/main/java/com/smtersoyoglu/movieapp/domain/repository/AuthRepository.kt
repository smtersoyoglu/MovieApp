package com.smtersoyoglu.movieapp.domain.repository

import com.smtersoyoglu.movieapp.common.Resource

interface AuthRepository {

    suspend fun signIn(email: String, password: String): Resource<String>
    suspend fun signUp(email: String, password: String): Resource<String>
    suspend fun sendPasswordResetEmail(email: String): Resource<String>
    fun signOut() : Resource<String>
    fun getCurrentUser(): String?

}