package com.smtersoyoglu.movieapp.data.mapper.movie

import com.smtersoyoglu.movieapp.data.source.remote.dto.CountryWatchProvidersDto
import com.smtersoyoglu.movieapp.data.source.remote.dto.MovieWatchProvidersDto
import com.smtersoyoglu.movieapp.data.source.remote.dto.WatchProviderDto
import com.smtersoyoglu.movieapp.domain.model.movie.CountryWatchProviders
import com.smtersoyoglu.movieapp.domain.model.movie.MovieWatchProviders
import com.smtersoyoglu.movieapp.domain.model.movie.WatchProvider

fun MovieWatchProvidersDto.toMovieWatchProviders(): MovieWatchProviders {
    return MovieWatchProviders(
        id = id,
        results = results.mapValues { (_, countryDto) -> countryDto.toCountryWatchProviders() }
    )
}

fun CountryWatchProvidersDto.toCountryWatchProviders(): CountryWatchProviders {
    return CountryWatchProviders(
        link = link,
        flatrate = flatrate?.map { it.toWatchProvider() },
        rent = rent?.map { it.toWatchProvider() },
        buy = buy?.map { it.toWatchProvider() },
        ads = ads?.map { it.toWatchProvider() },
        free = free?.map { it.toWatchProvider() }
    )
}

fun WatchProviderDto.toWatchProvider(): WatchProvider {
    return WatchProvider(
        logoPath = logoPath,
        providerId = providerId,
        providerName = providerName,
        displayPriority = displayPriority
    )
}