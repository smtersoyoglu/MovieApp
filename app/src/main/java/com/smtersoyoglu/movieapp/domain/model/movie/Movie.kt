package com.smtersoyoglu.movieapp.domain.model.movie

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String?,
    val backdropPath: String?,
    val releaseDate: String?,
    val voteAverage: Double,
    val adult: Boolean,
    val originalLanguage: String?,
    val originalTitle: String?,
    val popularity: Double,
    val video: Boolean,
    val voteCount: Int,
    val genreIds: List<Int>
)