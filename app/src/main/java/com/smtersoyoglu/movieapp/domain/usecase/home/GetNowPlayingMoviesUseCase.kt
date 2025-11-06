package com.smtersoyoglu.movieapp.domain.usecase.home

import androidx.paging.PagingData
import com.smtersoyoglu.movieapp.common.Resource
import com.smtersoyoglu.movieapp.domain.model.movie.Movie
import com.smtersoyoglu.movieapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNowPlayingMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) {
    operator fun invoke(
        language: String = "en-US",
        region: String? = null,
    ): Flow<PagingData<Movie>> {
        return movieRepository.getNowPlayingMovies(language, region)
    }
}