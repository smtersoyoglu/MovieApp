package com.smtersoyoglu.movieapp.data.source.remote

import com.smtersoyoglu.movieapp.data.source.remote.dto.GenreResponseDto
import com.smtersoyoglu.movieapp.data.source.remote.dto.MovieCreditsDto
import com.smtersoyoglu.movieapp.data.source.remote.dto.MovieDetailsDto
import com.smtersoyoglu.movieapp.data.source.remote.dto.MovieImagesDto
import com.smtersoyoglu.movieapp.data.source.remote.dto.MovieResponseDto
import com.smtersoyoglu.movieapp.data.source.remote.dto.MovieVideosResponseDto
import com.smtersoyoglu.movieapp.data.source.remote.dto.PersonDetailsDto
import com.smtersoyoglu.movieapp.data.source.remote.dto.PersonExternalIdsDto
import com.smtersoyoglu.movieapp.data.source.remote.dto.PersonImagesDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET("trending/movie/{time_window}")
    suspend fun getTrendingMovies(
        @Path("time_window") timeWindow: String = "week",
        @Query("language") language: String = "en-US"
    ): MovieResponseDto

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
        @Query("region") region: String? = null //region -> opsiyonel. Filmleri belirli bir ülkeye göre listelemek icin
    ): MovieResponseDto

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
        @Query("region") region: String? = null
    ): MovieResponseDto

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
        @Query("region") region: String? = null
    ): MovieResponseDto

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
        @Query("region") region: String? = null
    ): MovieResponseDto

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String = "en-US",
        @Query("append_to_response") appendToResponse: String? = null
    ): MovieDetailsDto

    @GET("movie/{movie_id}/images")
    suspend fun getMovieImages(
        @Path("movie_id") movieId: Int,
    ) : MovieImagesDto

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCredits(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String = "en-US"
    ) : MovieCreditsDto

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieVideos(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String = "en-US"
    ) : MovieVideosResponseDto

    @GET("movie/{movie_id}/similar")
    suspend fun getSimilarMovies(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): MovieResponseDto

    @GET("person/{person_id}")
    suspend fun getPersonDetails(
        @Path("person_id") personId: Int,
        @Query("append_to_response") appendToResponse: String? = null,
        @Query("language") language: String = "en-US"
    ): PersonDetailsDto

    @GET("person/{person_id}/movie_credits")
    suspend fun getPersonMovieCredits(
        @Path("person_id") personId: Int,
        @Query("language") language: String = "en-US"
    ): MovieCreditsDto


    @GET("person/{person_id}/external_ids")
    suspend fun getPersonExternalIds(
        @Path("person_id") personId: Int,
        @Query("language") language: String = "en-US"
    ): PersonExternalIdsDto

    @GET("person/{person_id}/images")
    suspend fun getPersonImages(
        @Path("person_id") personId: Int
    ): PersonImagesDto

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("page") page: Int = 1,
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("language") language: String = "en-US"
    ): MovieResponseDto

    @GET("genre/movie/list")
    suspend fun getGenres(
        @Query("language") language: String = "en-US"
    ): GenreResponseDto

    @GET("discover/movie")
    suspend fun getMoviesByGenre(
        @Query("with_genres") genreId: Int,
        @Query("page") page: Int = 1,
        @Query("language") language: String = "en-US"
    ): MovieResponseDto
}