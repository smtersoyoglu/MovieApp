package com.smtersoyoglu.movieapp.domain.usecase.search

import androidx.paging.PagingData
import com.smtersoyoglu.movieapp.domain.model.movie.Movie
import com.smtersoyoglu.movieapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSearchMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) {
    operator fun invoke(
        query: String,
        includeAdult: Boolean = false,
        language: String = "en-US",
    ): Flow<PagingData<Movie>> {
        return movieRepository.getSearchMovies(query, includeAdult, language)
    }
}