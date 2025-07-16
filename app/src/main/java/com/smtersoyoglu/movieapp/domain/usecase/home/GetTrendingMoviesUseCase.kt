package com.smtersoyoglu.movieapp.domain.usecase.home

import com.smtersoyoglu.movieapp.common.Resource
import com.smtersoyoglu.movieapp.domain.model.movie.Movie
import com.smtersoyoglu.movieapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTrendingMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) {
    operator fun invoke(
        timeWindow: String = "week",
        language: String = "en-US",
    ): Flow<Resource<List<Movie>>> {
        return movieRepository.getTrendingMovies(timeWindow, language)
    }
}