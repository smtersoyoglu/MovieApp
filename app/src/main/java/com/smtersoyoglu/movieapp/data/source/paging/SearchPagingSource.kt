package com.smtersoyoglu.movieapp.data.source.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.smtersoyoglu.movieapp.data.mapper.movie.toMovie
import com.smtersoyoglu.movieapp.data.source.remote.MovieService
import com.smtersoyoglu.movieapp.domain.model.movie.Movie
import retrofit2.HttpException
import java.io.IOException

class SearchPagingSource(
    private val movieService: MovieService,
    private val query: String,
    private val includeAdult: Boolean = false,
    private val language: String = "en-US",
    ) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val page = params.key ?: 1
            
            val response = movieService.searchMovies(
                query = query,
                page = page,
                includeAdult = includeAdult,
                language = language
            )

            LoadResult.Page(
                data = response.results.map { it.toMovie() },
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (page >= response.totalPages) null else page + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}