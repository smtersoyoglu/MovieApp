package com.smtersoyoglu.movieapp.data.source.remote.dto

import com.google.gson.annotations.SerializedName

data class PersonImagesDto(
    @SerializedName("id") val id: Int,
    @SerializedName("profiles") val profiles: List<PersonImageDto>
)

data class PersonImageDto(
    @SerializedName("aspect_ratio") val aspectRatio: Double,
    @SerializedName("height") val height: Int,
    @SerializedName("iso_639_1") val iso6391: String?,
    @SerializedName("file_path") val filePath: String,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("vote_count") val voteCount: Int,
    @SerializedName("width") val width: Int
)