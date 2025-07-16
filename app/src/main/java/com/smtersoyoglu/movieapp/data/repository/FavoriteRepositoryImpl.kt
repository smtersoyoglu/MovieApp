package com.smtersoyoglu.movieapp.data.repository

import com.smtersoyoglu.movieapp.data.mapper.favorite.toFavoriteMovie
import com.smtersoyoglu.movieapp.data.mapper.favorite.toFavoriteMovieEntity
import com.smtersoyoglu.movieapp.data.source.local.FavoriteDao
import com.smtersoyoglu.movieapp.domain.model.favorite.FavoriteMovie
import com.smtersoyoglu.movieapp.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val favoriteDao: FavoriteDao
) : FavoriteRepository {

    override suspend fun insertFavorite(favoriteMovie: FavoriteMovie) = favoriteDao.insertFavorite(favoriteMovie.toFavoriteMovieEntity())

    override suspend fun deleteFavorite(favoriteMovie: FavoriteMovie) = favoriteDao.deleteFavorite(favoriteMovie.toFavoriteMovieEntity())

    override fun getAllFavorites(): Flow<List<FavoriteMovie>> = favoriteDao.getAllFavorites().map { it.map { it.toFavoriteMovie() } }

    override suspend fun isFavorite(movieId: Int): Boolean = favoriteDao.isFavorite(movieId)
}