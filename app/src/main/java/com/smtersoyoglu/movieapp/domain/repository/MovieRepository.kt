package com.smtersoyoglu.movieapp.domain.repository

import com.smtersoyoglu.movieapp.common.Resource
import com.smtersoyoglu.movieapp.domain.model.Movie
import com.smtersoyoglu.movieapp.domain.model.MovieCredits
import com.smtersoyoglu.movieapp.domain.model.MovieDetails
import com.smtersoyoglu.movieapp.domain.model.MovieVideos
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getTrendingMovies(timeWindow: String = "week", language: String = "en-US"): Flow<Resource<List<Movie>>>

    fun getNowPlayingMovies(language: String = "en-US", page: Int = 1, region: String? = null, ): Flow<Resource<List<Movie>>>

    fun getPopularMovies(language: String = "en-US", page: Int = 1, region: String? = null, ): Flow<Resource<List<Movie>>>

    fun getTopRatedMovies(language: String = "en-US", page: Int = 1, region: String? = null, ): Flow<Resource<List<Movie>>>

    fun getUpcomingMovies(language: String = "en-US", page: Int = 1, region: String? = null, ): Flow<Resource<List<Movie>>>

    suspend fun getMovieDetails(movieId: Int, language: String = "en-US", appendToResponse: String? = null): Resource<MovieDetails>

    suspend fun getMovieCredits(movieId: Int, language: String = "en-US") : Resource<MovieCredits>

    suspend fun getMovieVideos(movieId: Int, language: String = "en-US") : Resource<MovieVideos>
}