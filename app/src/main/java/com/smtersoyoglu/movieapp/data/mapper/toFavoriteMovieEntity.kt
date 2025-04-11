package com.smtersoyoglu.movieapp.data.mapper

import com.smtersoyoglu.movieapp.data.source.local.entity.FavoriteMovieEntity
import com.smtersoyoglu.movieapp.domain.model.FavoriteMovie
import com.smtersoyoglu.movieapp.domain.model.Genre

fun FavoriteMovie.toFavoriteMovieEntity(): FavoriteMovieEntity = FavoriteMovieEntity(
    id = id,
    title = title,
    posterPath = posterPath,
    overview = overview ?: "",
    releaseDate = releaseDate ?: "",
    voteAverage = voteAverage ?: 0.0,
    runtime = runtime ?: 0,
    genres = genres,
    addedDate = addedDate
)

fun FavoriteMovieEntity.toFavoriteMovie(): FavoriteMovie = FavoriteMovie(
    id = id,
    title = title,
    posterPath = posterPath,
    overview = overview,
    releaseDate = releaseDate,
    runtime = runtime,
    voteAverage = voteAverage,
    genres = genres,
    addedDate = addedDate
)

fun List<Genre>.toGenreString(): String? {
    return if (isNotEmpty()) {
        joinToString(", ") { it.name }
    } else {
        null
    }
}