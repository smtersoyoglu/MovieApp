package com.smtersoyoglu.movieapp.domain.model.movie

data class MovieWatchProviders(
    val id: Int,
    val results: Map<String, CountryWatchProviders>
)

data class CountryWatchProviders(
    val link: String,
    val flatrate: List<WatchProvider>?,
    val rent: List<WatchProvider>?,
    val buy: List<WatchProvider>?,
    val ads: List<WatchProvider>?,
    val free: List<WatchProvider>?
)

data class WatchProvider(
    val logoPath: String?,
    val providerId: Int,
    val providerName: String,
    val displayPriority: Int
)