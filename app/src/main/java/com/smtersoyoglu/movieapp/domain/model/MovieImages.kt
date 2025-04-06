package com.smtersoyoglu.movieapp.domain.model

data class MovieImages(
    val backdrops: List<MovieImage>,
    val posters: List<MovieImage>
)

data class MovieImage(
    val filePath: String,
    val voteAverage: Double,
    val voteCount: Int
)