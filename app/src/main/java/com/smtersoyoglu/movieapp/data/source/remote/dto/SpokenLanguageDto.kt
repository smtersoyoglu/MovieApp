package com.smtersoyoglu.movieapp.data.source.remote.dto

import com.google.gson.annotations.SerializedName

data class SpokenLanguageDto(
    @SerializedName("english_name") val englishName: String,
    @SerializedName("iso_639_1") val iso639_1: String,
    @SerializedName("name") val name: String
)