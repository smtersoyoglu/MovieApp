package com.smtersoyoglu.movieapp.domain.repository

import androidx.paging.PagingData
import com.smtersoyoglu.movieapp.common.Resource
import com.smtersoyoglu.movieapp.domain.model.movie.Genre
import com.smtersoyoglu.movieapp.domain.model.movie.Movie
import com.smtersoyoglu.movieapp.domain.model.movie.MovieCredits
import com.smtersoyoglu.movieapp.domain.model.movie.MovieDetails
import com.smtersoyoglu.movieapp.domain.model.movie.MovieImages
import com.smtersoyoglu.movieapp.domain.model.movie.MovieVideos
import com.smtersoyoglu.movieapp.domain.model.movie.MovieWatchProviders
import com.smtersoyoglu.movieapp.domain.model.person.PersonDetails
import com.smtersoyoglu.movieapp.domain.model.person.PersonExternalIds
import com.smtersoyoglu.movieapp.domain.model.person.PersonImage
import com.smtersoyoglu.movieapp.domain.model.person.PersonMovieCredits
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getTrendingMovies(timeWindow: String = "week", language: String = "en-US"): Flow<Resource<List<Movie>>>

    fun getNowPlayingMovies(language: String = "en-US", region: String? = null): Flow<PagingData<Movie>>

    fun getPopularMovies(language: String = "en-US", region: String? = null, ): Flow<PagingData<Movie>>

    fun getTopRatedMovies(language: String = "en-US", region: String? = null, ): Flow<PagingData<Movie>>

    fun getUpcomingMovies(language: String = "en-US", region: String? = null, ): Flow<PagingData<Movie>>

    suspend fun getMovieDetails(movieId: Int, language: String = "en-US", appendToResponse: String? = null): Resource<MovieDetails>

    suspend fun getMovieCredits(movieId: Int, language: String = "en-US") : Resource<MovieCredits>

    suspend fun getMovieVideos(movieId: Int, language: String = "en-US") : Resource<MovieVideos>

    suspend fun getMovieWatchProviders(movieId: Int): Resource<MovieWatchProviders>

    suspend fun getSimilarMovies(movieId: Int, language: String = "en-US", page: Int = 1) : Resource<List<Movie>>

    suspend fun getPersonDetails(personId: Int, appendToResponse: String? = null, language: String = "en-US") : Resource<PersonDetails>

    suspend fun getMovieImages(movieId: Int): Resource<MovieImages>

    suspend fun getPersonMovieCredits(personId: Int, language: String = "en-US") : Resource<PersonMovieCredits>

    suspend fun getPersonExternalIds(personId: Int, language: String = "en-US"): Resource<PersonExternalIds>

    suspend fun getPersonImages(personId: Int): Resource<List<PersonImage>>

    fun getSearchMovies(query: String, includeAdult: Boolean = false, language: String = "en-US"): Flow<PagingData<Movie>>

    suspend fun getGenres(language: String = "en-US") : Resource<List<Genre>>

    suspend fun getMoviesByGenre(genreId: Int, page: Int = 1, language: String = "en-US") : Resource<List<Movie>>


}