package com.smtersoyoglu.movieapp.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.smtersoyoglu.movieapp.common.Resource
import com.smtersoyoglu.movieapp.domain.repository.AuthRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
) : AuthRepository {
    override suspend fun signIn(email: String, password: String): Resource<String> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            Resource.Success(result.user?.uid.orEmpty())
        } catch (e: Exception) {
            val errorMessage = when (e) {
                is FirebaseAuthInvalidCredentialsException -> "Invalid email or password"
                is FirebaseAuthInvalidUserException -> "Account not found"
                else -> "Sign-in failed: ${e.message}"
            }
            Resource.Error(errorMessage)
        }
    }

    override suspend fun signUp(email: String, password: String): Resource<String> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            Resource.Success(result.user?.uid.orEmpty())
        } catch (e: Exception) {
            val errorMessage = when (e) {
                is FirebaseAuthUserCollisionException -> "This email is already in use"
                else -> "Sign-up failed: ${e.message}"
            }
            Resource.Error(errorMessage)
        }
    }

    override suspend fun sendPasswordResetEmail(email: String): Resource<String> {
        return try {
            firebaseAuth.sendPasswordResetEmail(email).await()
            Resource.Success("Password reset email sent")
        } catch (e: Exception) {
            Resource.Error("Password reset failed: ${e.message}")
        }
    }

    override suspend fun signOut(): Resource<String> {
        return try {
            firebaseAuth.signOut()
            Resource.Success("Signed out successfully")
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Failed to sign out")
        }
    }

    override suspend fun getCurrentUser(): String? {
        return firebaseAuth.currentUser?.uid
    }

}