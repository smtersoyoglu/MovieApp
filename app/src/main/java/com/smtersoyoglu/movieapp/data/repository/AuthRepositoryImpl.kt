package com.smtersoyoglu.movieapp.data.repository

import com.google.firebase.auth.FirebaseAuth
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
            Resource.Error(e.message ?: "Bilinmeyen bir hata oluştu")
        }

    }

    override suspend fun signUp(email: String, password: String): Resource<String> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            Resource.Success(result.user?.uid.orEmpty())

        } catch (e: Exception) {
            Resource.Error(e.message ?: "Bilinmeyen bir hata oluştu")
        }
    }

    override suspend fun sendPasswordResetEmail(email: String): Resource<String> {
        return try {
            firebaseAuth.sendPasswordResetEmail(email).await()
            Resource.Success("Şifre sıfırlama maili gönderildi")
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Şifre sıfırlama başarısız")
        }
    }

    override fun signOut(): Resource<String> {
        return try {
            firebaseAuth.signOut()
            Resource.Success("Çıkış yapıldı")
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Çıkış yaparken hata oluştu")
        }
    }

    override fun getCurrentUser(): String? {
        return firebaseAuth.currentUser?.uid
    }

}