package com.smtersoyoglu.movieapp.domain.repository

import com.smtersoyoglu.movieapp.domain.model.favorite.FavoriteMovie
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {

    suspend fun insertFavorite(favoriteMovie: FavoriteMovie)

    suspend fun deleteFavorite(favoriteMovie: FavoriteMovie)

    fun getAllFavorites(): Flow<List<FavoriteMovie>>

    suspend fun isFavorite(movieId: Int): Boolean
}