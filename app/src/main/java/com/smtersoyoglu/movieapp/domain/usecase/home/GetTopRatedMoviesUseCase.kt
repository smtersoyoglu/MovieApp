package com.smtersoyoglu.movieapp.domain.usecase.home

import com.smtersoyoglu.movieapp.common.Resource
import com.smtersoyoglu.movieapp.domain.model.movie.Movie
import com.smtersoyoglu.movieapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTopRatedMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) {
    operator fun invoke(
        language: String = "en-US",
        page: Int = 1,
        region: String? = null,
    ) : Flow<Resource<List<Movie>>> {
        return movieRepository.getTopRatedMovies(language, page, region)
    }
}