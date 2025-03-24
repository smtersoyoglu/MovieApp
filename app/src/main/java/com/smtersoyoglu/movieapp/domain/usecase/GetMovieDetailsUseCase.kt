package com.smtersoyoglu.movieapp.domain.usecase

import com.smtersoyoglu.movieapp.common.Resource
import com.smtersoyoglu.movieapp.domain.model.MovieDetails
import com.smtersoyoglu.movieapp.domain.repository.MovieRepository
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) {
    suspend operator fun invoke(
        movieId: Int,
        language: String = "en-US",
        appendToResponse: String? = null,
    ) : Resource<MovieDetails> {
        return movieRepository.getMovieDetails(movieId, language, appendToResponse)
    }
}