package com.smtersoyoglu.movieapp.domain.usecase.search

import com.smtersoyoglu.movieapp.common.Resource
import com.smtersoyoglu.movieapp.domain.model.movie.Movie
import com.smtersoyoglu.movieapp.domain.repository.MovieRepository
import javax.inject.Inject

class GetSearchMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) {
    suspend operator fun invoke(
        query: String,
        page: Int = 1,
        includeAdult: Boolean = false,
        language: String = "en-US",
    ): Resource<List<Movie>> {
        return movieRepository.getSearchMovies(query, page, includeAdult, language)
    }
}