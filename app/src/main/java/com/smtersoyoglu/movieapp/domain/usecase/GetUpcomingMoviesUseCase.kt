package com.smtersoyoglu.movieapp.domain.usecase

import com.smtersoyoglu.movieapp.common.Resource
import com.smtersoyoglu.movieapp.domain.model.Movie
import com.smtersoyoglu.movieapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUpcomingMoviesUseCase @Inject constructor(
    private val repository: MovieRepository,
) {
    operator fun invoke(
        language: String = "en-US",
        page: Int = 1,
        region: String? = null,
    ): Flow<Resource<List<Movie>>> {
        return repository.getUpcomingMovies(language, page, region)
    }
}