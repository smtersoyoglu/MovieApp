package com.smtersoyoglu.movieapp.domain.model

data class FavoriteMovie(
    val id: Int,
    val title: String,
    val posterPath: String?,
    val overview: String?,
    val releaseDate: String?,
    val runtime: Int?,
    val voteAverage: Double?,
    val genres: String?,
    val addedDate: Long
)