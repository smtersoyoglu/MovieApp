package com.smtersoyoglu.movieapp.domain.repository

import com.smtersoyoglu.movieapp.domain.model.favorite.FavoriteMovie
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    suspend fun insertFavorite(favoriteMovie: FavoriteMovie)
    suspend fun deleteFavorite(movieId: Int)
    fun getAllFavorites(): Flow<List<FavoriteMovie>>
    fun isFavorite(movieId: Int): Flow<Boolean>
}