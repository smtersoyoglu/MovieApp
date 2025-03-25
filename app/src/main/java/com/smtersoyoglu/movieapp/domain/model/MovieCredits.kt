package com.smtersoyoglu.movieapp.domain.model

data class MovieCredits(
    val id: Int,
    val cast: List<Cast>,
    val crew: List<Crew>
)

data class Cast(
    val id: Int,
    val name: String,
    val character: String,
    val profilePath: String?
)

data class Crew(
    val id: Int,
    val name: String,
    val department: String,
    val job: String,
    val profilePath: String?
)