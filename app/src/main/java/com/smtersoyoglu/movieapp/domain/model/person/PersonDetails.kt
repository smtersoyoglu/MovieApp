package com.smtersoyoglu.movieapp.domain.model.person

data class PersonDetails(
    val id: Int,
    val name: String,
    val biography: String,
    val birthday: String?,
    val deathday: String?,
    val gender: Int,
    val knownForDepartment: String,
    val placeOfBirth: String?,
    val popularity: Double,
    val profilePath: String?,
    val alsoKnownAs: List<String>,
    val imdbId: String?
)