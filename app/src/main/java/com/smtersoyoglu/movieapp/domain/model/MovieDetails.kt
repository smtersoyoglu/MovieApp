package com.smtersoyoglu.movieapp.domain.model

data class MovieDetails(
    val id: Int,
    val title: String,
    val overview: String?,
    val posterPath: String?,
    val backdropPath: String?,
    val releaseDate: String?,
    val voteAverage: Double,
    val genres: List<Genre>,
    val runtime: Int?,
    val tagline: String?,
    val status: String,
    val originalLanguage: String,
    val originalTitle: String,
    val popularity: Double,
    val budget: Long,
    val revenue: Long,
    val homepage: String?,
    val imdbId: String?,
    val productionCompanies: List<ProductionCompany>,
    val productionCountries: List<ProductionCountry>,
    val spokenLanguages: List<SpokenLanguage>,
    val belongsToCollection: Any?
)
data class Genre(
    val id: Int,
    val name: String
)

data class ProductionCompany(
    val id: Int,
    val name: String,
    val logoPath: String?,
    val originCountry: String
)

data class ProductionCountry(
    val iso3166_1: String,
    val name: String
)

data class SpokenLanguage(
    val englishName: String,
    val iso639_1: String,
    val name: String
)
