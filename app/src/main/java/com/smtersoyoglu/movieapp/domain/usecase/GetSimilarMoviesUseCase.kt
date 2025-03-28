package com.smtersoyoglu.movieapp.domain.usecase

import com.smtersoyoglu.movieapp.common.Resource
import com.smtersoyoglu.movieapp.domain.model.Movie
import com.smtersoyoglu.movieapp.domain.repository.MovieRepository
import javax.inject.Inject

class GetSimilarMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) {
    suspend operator fun invoke(
        movieId: Int,
        language: String = "en-US",
        page: Int = 1,
    ): Resource<List<Movie>> {
        return movieRepository.getSimilarMovies(movieId, language, page)
    }
}