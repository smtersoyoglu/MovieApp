package com.smtersoyoglu.movieapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.smtersoyoglu.movieapp.common.Constants.PAGE_SIZE
import com.smtersoyoglu.movieapp.common.Resource
import com.smtersoyoglu.movieapp.data.mapper.movie.toCredits
import com.smtersoyoglu.movieapp.data.mapper.movie.toGenreList
import com.smtersoyoglu.movieapp.data.mapper.movie.toMovie
import com.smtersoyoglu.movieapp.data.mapper.movie.toMovieDetails
import com.smtersoyoglu.movieapp.data.mapper.movie.toMovieImages
import com.smtersoyoglu.movieapp.data.mapper.movie.toMovieVideos
import com.smtersoyoglu.movieapp.data.mapper.movie.toMovieWatchProviders
import com.smtersoyoglu.movieapp.data.mapper.person.toPersonDetails
import com.smtersoyoglu.movieapp.data.mapper.person.toPersonExternalIds
import com.smtersoyoglu.movieapp.data.mapper.person.toPersonImages
import com.smtersoyoglu.movieapp.data.mapper.person.toPersonMovieCredits
import com.smtersoyoglu.movieapp.data.source.paging.MoviePagingSource
import com.smtersoyoglu.movieapp.data.source.paging.SearchPagingSource
import com.smtersoyoglu.movieapp.data.source.remote.MovieService
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
import com.smtersoyoglu.movieapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieService: MovieService,
) : MovieRepository {

    override fun getTrendingMovies(
        timeWindow: String,
        language: String,
    ): Flow<Resource<List<Movie>>> = flow {
        try {
            val response = movieService.getTrendingMovies(timeWindow, language)
            val movies = response.results.map { it.toMovie() }
            emit(Resource.Success(movies))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An error occurred while fetching trending movies"))
        }
    }

    override fun getNowPlayingMovies(
        language: String,
        region: String?,
    ): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false, prefetchDistance = 3),
            pagingSourceFactory = {
                MoviePagingSource(
                    movieService = movieService,
                    movieType = MoviePagingSource.MovieType.NOW_PLAYING,
                    language = language,
                    region = region
                )
            }
        ).flow
    }

    override fun getPopularMovies(
        language: String,
        region: String?,
    ): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false, prefetchDistance = 3),
            pagingSourceFactory = {
                MoviePagingSource(
                    movieService = movieService,
                    movieType = MoviePagingSource.MovieType.POPULAR,
                    language = language,
                    region = region
                )
            }
        ).flow
    }

    override fun getTopRatedMovies(
        language: String,
        region: String?,
    ): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false, prefetchDistance = 3),
            pagingSourceFactory = {
                MoviePagingSource(
                    movieService = movieService,
                    movieType = MoviePagingSource.MovieType.TOP_RATED,
                    language = language,
                    region = region
                )
            }
        ).flow
    }


    override fun getUpcomingMovies(
        language: String,
        region: String?
    ): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false, prefetchDistance = 3),
            pagingSourceFactory = {
                MoviePagingSource(
                    movieService = movieService,
                    movieType = MoviePagingSource.MovieType.UPCOMING,
                    language = language,
                    region = region
                )
            }
        ).flow
    }

    override suspend fun getMovieDetails(
        movieId: Int,
        language: String,
        appendToResponse: String?,
    ): Resource<MovieDetails> {
        return try {
            val response = movieService.getMovieDetails(movieId, language, appendToResponse)
            Resource.Success(response.toMovieDetails())
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred while fetching movie details")
        }
    }

    override suspend fun getMovieCredits(
        movieId: Int,
        language: String,
    ): Resource<MovieCredits> {
        return try {
            val response = movieService.getMovieCredits(movieId, language)
            Resource.Success(response.toCredits())
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred while fetching movie credits")
        }
    }

    override suspend fun getMovieVideos(
        movieId: Int,
        language: String,
    ): Resource<MovieVideos> {
        return try {
            val response = movieService.getMovieVideos(movieId, language)
            Resource.Success(response.toMovieVideos())
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred while fetching movie videos")
        }
    }

    override suspend fun getMovieWatchProviders(movieId: Int): Resource<MovieWatchProviders> {
        return try {
            val response = movieService.getMovieWatchProviders(movieId)
            Resource.Success(response.toMovieWatchProviders())
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred while fetching movie watch providers")
        }
    }

    override suspend fun getSimilarMovies(
        movieId: Int,
        language: String,
        page: Int,
    ): Resource<List<Movie>> {
        return try {
            val response = movieService.getSimilarMovies(movieId, language, page)
            val movies = response.results.map { it.toMovie() }
            Resource.Success(movies)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred while fetching similar movies")
        }
    }

    override suspend fun getPersonDetails(
        personId: Int,
        appendToResponse: String?,
        language: String,
    ): Resource<PersonDetails> {
        return try {
            val response = movieService.getPersonDetails(personId, appendToResponse, language)
            Resource.Success(response.toPersonDetails())
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred while fetching person details")
        }
    }

    override suspend fun getMovieImages(movieId: Int): Resource<MovieImages> {
        return try {
            val response = movieService.getMovieImages(movieId)
            Resource.Success(response.toMovieImages())
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred while fetching movie images")
        }
    }

    override suspend fun getPersonMovieCredits(
        personId: Int,
        language: String,
    ): Resource<PersonMovieCredits> {
        return try {
            val response = movieService.getPersonMovieCredits(personId, language)
            Resource.Success(response.toPersonMovieCredits())
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred while fetching person movie credits")
        }
    }

    override suspend fun getPersonExternalIds(
        personId: Int,
        language: String,
    ): Resource<PersonExternalIds> {
        return try {
            val response = movieService.getPersonExternalIds(personId, language)
            Resource.Success(response.toPersonExternalIds())
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred while fetching person external ids")
        }
    }

    override suspend fun getPersonImages(personId: Int): Resource<List<PersonImage>> {
        return try {
            val response = movieService.getPersonImages(personId)
            Resource.Success(response.toPersonImages())
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred while fetching person images")
        }
    }

    override fun getSearchMovies(
        query: String,
        includeAdult: Boolean,
        language: String
    ): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false, prefetchDistance = 3),
            pagingSourceFactory = {
                SearchPagingSource(
                    movieService = movieService,
                    query = query,
                    language = language,
                    includeAdult = includeAdult
                )
            }
        ).flow
    }

    override suspend fun getGenres(language: String): Resource<List<Genre>> {
        return try {
            val response = movieService.getGenres(language)
            Resource.Success(response.toGenreList())
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred while fetching genres")
        }

    }

    override suspend fun getMoviesByGenre(
        genreId: Int,
        page: Int,
        language: String,
    ): Resource<List<Movie>> {
        return try {
            val response = movieService.getMoviesByGenre(genreId, page, language)
            val movies = response.results.map { it.toMovie() }
            Resource.Success(movies)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred while fetching movies by genre")
        }
    }

}