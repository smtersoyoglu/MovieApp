package com.smtersoyoglu.movieapp.data.source.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.smtersoyoglu.movieapp.data.mapper.movie.toMovie
import com.smtersoyoglu.movieapp.data.source.remote.MovieService
import com.smtersoyoglu.movieapp.domain.model.movie.Movie
import retrofit2.HttpException
import java.io.IOException

class MoviePagingSource(
    private val movieService: MovieService,
    private val movieType: MovieType,
    private val language: String = "en-US",
    private val region: String? = null
) : PagingSource<Int, Movie>() {

    // Bu enum ile hangi film listesini çekeceğimizi belirtiyoruz
    enum class MovieType {
        NOW_PLAYING,    // Şu anda oynayan filmler
        POPULAR,        // Popüler filmler
        TOP_RATED,      // En yüksek puanlı filmler
        UPCOMING        // Yakında çıkacak filmler
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            // Hangi sayfayı yükleyeceğimizi belirle (ilk sayfa = 1)
            val page = params.key ?: 1

            // MovieType'a göre hangi API'yi çağıracağımızı belirle
            val response = when (movieType) {
                MovieType.NOW_PLAYING -> movieService.getNowPlayingMovies(language, page, region)
                MovieType.POPULAR -> movieService.getPopularMovies(language, page, region)
                MovieType.TOP_RATED -> movieService.getTopRatedMovies(language, page, region)
                MovieType.UPCOMING -> movieService.getUpcomingMovies(language, page, region)
            }

            // Başarılı sonuç döndür
            LoadResult.Page(
                data = response.results.map { it.toMovie() }, // DTO'yu domain model'e çevir
                prevKey = if (page == 1) null else page - 1,  // Önceki sayfa
                nextKey = if (page >= response.totalPages) null else page + 1 // Sonraki sayfa
            )
        } catch (exception: IOException) {
            // İnternet hatası
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            // API hatası
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        // Sayfa yenilendiğinde hangi sayfadan başlayacağımızı belirle
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}