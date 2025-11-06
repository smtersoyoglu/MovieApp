package com.smtersoyoglu.movieapp.data.source.remote.dto

import com.google.gson.annotations.SerializedName

data class MovieWatchProvidersDto(
    @SerializedName("id") val id: Int,
    @SerializedName("results") val results: Map<String, CountryWatchProvidersDto>
)

data class CountryWatchProvidersDto(
    @SerializedName("link") val link: String,
    @SerializedName("flatrate") val flatrate: List<WatchProviderDto>?,
    @SerializedName("rent") val rent: List<WatchProviderDto>?,
    @SerializedName("buy") val buy: List<WatchProviderDto>?,
    @SerializedName("ads") val ads: List<WatchProviderDto>?,
    @SerializedName("free") val free: List<WatchProviderDto>?
)

data class WatchProviderDto(
    @SerializedName("logo_path") val logoPath: String?,
    @SerializedName("provider_id") val providerId: Int,
    @SerializedName("provider_name") val providerName: String,
    @SerializedName("display_priority") val displayPriority: Int
)