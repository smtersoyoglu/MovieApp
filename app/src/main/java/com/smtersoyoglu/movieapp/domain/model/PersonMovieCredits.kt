package com.smtersoyoglu.movieapp.domain.model

data class PersonMovieCredits(
    val cast: List<PersonMovieCast>
)

data class PersonMovieCast(
    val id: Int,
    val title: String,
    val posterPath: String?,
    val releaseDate: String?,
    val character: String?
)