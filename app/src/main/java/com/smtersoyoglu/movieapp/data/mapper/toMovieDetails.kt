package com.smtersoyoglu.movieapp.data.mapper

import com.smtersoyoglu.movieapp.data.source.remote.dto.GenreDto
import com.smtersoyoglu.movieapp.data.source.remote.dto.GenreResponseDto
import com.smtersoyoglu.movieapp.data.source.remote.dto.MovieDetailsDto
import com.smtersoyoglu.movieapp.data.source.remote.dto.ProductionCompanyDto
import com.smtersoyoglu.movieapp.data.source.remote.dto.ProductionCountryDto
import com.smtersoyoglu.movieapp.data.source.remote.dto.SpokenLanguageDto
import com.smtersoyoglu.movieapp.domain.model.Genre
import com.smtersoyoglu.movieapp.domain.model.MovieDetails
import com.smtersoyoglu.movieapp.domain.model.ProductionCompany
import com.smtersoyoglu.movieapp.domain.model.ProductionCountry
import com.smtersoyoglu.movieapp.domain.model.SpokenLanguage

fun MovieDetailsDto.toMovieDetails(): MovieDetails {
    return MovieDetails(
        id = id,
        title = title,
        overview = overview,
        posterPath = posterPath,
        backdropPath = backdropPath,
        releaseDate = releaseDate,
        voteAverage = voteAverage,
        genres = genres.map { it.toGenre() },
        runtime = runtime,
        tagline = tagline,
        status = status,
        originalLanguage = originalLanguage,
        originalTitle = originalTitle,
        popularity = popularity,
        budget = budget,
        revenue = revenue,
        homepage = homepage,
        imdbId = imdbId,
        productionCompanies = productionCompanies.map { it.toProductionCompany() },
        productionCountries = productionCountries.map { it.toProductionCountry() },
        spokenLanguages = spokenLanguages.map { it.toSpokenLanguage() },
        belongsToCollection = belongsToCollection
    )
}

fun GenreDto.toGenre(): Genre {
    return Genre(id = id, name = name)
}


fun GenreResponseDto.toGenreList(): List<Genre> {
    return genres.map { it.toGenre() }
}

fun ProductionCompanyDto.toProductionCompany(): ProductionCompany {
    return ProductionCompany(
        id = id,
        name = name,
        logoPath = logoPath,
        originCountry = originCountry
    )
}

fun ProductionCountryDto.toProductionCountry(): ProductionCountry {
    return ProductionCountry(iso3166_1 = iso3166_1, name = name)
}

fun SpokenLanguageDto.toSpokenLanguage(): SpokenLanguage {
    return SpokenLanguage(
        englishName = englishName,
        iso639_1 = iso639_1,
        name = name
    )
}

