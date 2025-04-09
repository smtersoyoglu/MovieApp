package com.smtersoyoglu.movieapp.data.mapper

import com.smtersoyoglu.movieapp.data.source.local.entity.FavoriteMovieEntity
import com.smtersoyoglu.movieapp.domain.model.FavoriteMovie

fun FavoriteMovie.toFavoriteMovieEntity(): FavoriteMovieEntity = FavoriteMovieEntity(
    id = id,
    title = title,
    posterPath = posterPath,
)

fun FavoriteMovieEntity.toFavoriteMovie(): FavoriteMovie = FavoriteMovie(
    id = id,
    title = title,
    posterPath = posterPath,
)