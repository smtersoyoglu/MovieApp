package com.smtersoyoglu.movieapp.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.firestore.FirebaseFirestore
import com.smtersoyoglu.movieapp.common.Resource
import com.smtersoyoglu.movieapp.domain.model.user.UserProfile
import com.smtersoyoglu.movieapp.domain.repository.AuthRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
) : AuthRepository {
    override suspend fun signIn(email: String, password: String): Resource<String> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            Resource.Success(result.user?.uid ?: return Resource.Error("User ID not found"))
        } catch (e: Exception) {
            val errorMessage = when (e) {
                is FirebaseAuthInvalidCredentialsException -> "Invalid email or password"
                is FirebaseAuthInvalidUserException -> "Account not found"
                else -> "Sign-in failed: ${e.message}"
            }
            Resource.Error(errorMessage)
        }
    }

    override suspend fun signUp(
        email: String,
        password: String,
        fullName: String,
    ): Resource<String> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val userId = result.user?.uid ?: return Resource.Error("User ID not found")
            val userProfile = UserProfile(userId, fullName, email)
            firestore.collection("users").document(userId).set(userProfile).await()
            Resource.Success(userId)
        } catch (e: Exception) {
            val errorMessage = when (e) {
                is FirebaseAuthUserCollisionException -> "This email is already in use"
                else -> "Sign-up failed: ${e.message}"
            }
            Resource.Error(errorMessage)
        }
    }

    override suspend fun getUserProfile(): Resource<UserProfile> {
        val userId =
            firebaseAuth.currentUser?.uid ?: return Resource.Error("User not logged in")
        return try {
            val document = firestore.collection("users").document(userId).get().await()
            val userProfile = document.toObject(UserProfile::class.java)
            if (userProfile != null) {
                Resource.Success(userProfile)
            } else {
                Resource.Error("User profile not found")
            }
        } catch (e: Exception) {
            Resource.Error("Profile not available: ${e.message}")
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

    override suspend fun isUserSignedIn(): Boolean {
        return firebaseAuth.currentUser != null
    }

    override suspend fun getCurrentUser(): String? {
        return firebaseAuth.currentUser?.uid
    }

}