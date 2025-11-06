package com.smtersoyoglu.movieapp.presentation.search.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.smtersoyoglu.movieapp.R
import com.smtersoyoglu.movieapp.domain.model.movie.Movie
import com.smtersoyoglu.movieapp.presentation.components.EmptySearchState
import com.smtersoyoglu.movieapp.presentation.components.ErrorScreen
import com.smtersoyoglu.movieapp.presentation.components.LoadingBar

@Composable
fun SearchMoviesContent(
    pagingItems: LazyPagingItems<Movie>,
    searchQuery: String,
    onMovieClick: (Int) -> Unit,
    onRetry: () -> Unit
) {
    when {
        pagingItems.loadState.refresh is LoadState.Loading -> {
            LoadingBar()
        }
        pagingItems.loadState.refresh is LoadState.Error -> {
            val error = (pagingItems.loadState.refresh as LoadState.Error).error
            ErrorScreen(
                message = error.localizedMessage ?: stringResource(R.string.error_message, ""),
                onRetry = onRetry
            )
        }
        pagingItems.itemCount == 0 -> {
            EmptySearchState(query = searchQuery)
        }
        else -> {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(21.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.padding(top = 12.dp)
            ) {
                items(
                    count = pagingItems.itemCount,
                    key = { index -> pagingItems[index]?.id ?: index }
                ) { index ->
                    pagingItems[index]?.let { movie ->
                        MovieGridItem(
                            movie = movie,
                            onMovieClick = onMovieClick
                        )
                    }
                }
                if (pagingItems.loadState.append is LoadState.Loading) {
                    item(span = { GridItemSpan(3) }) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(color = Color.White)
                        }
                    }
                }
                if (pagingItems.loadState.append is LoadState.Error) {
                    item(span = { GridItemSpan(3) }) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = stringResource(R.string.error_loading_more),
                                color = Color.White,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
        }
    }
}