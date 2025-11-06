package com.smtersoyoglu.movieapp.presentation.search.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.smtersoyoglu.movieapp.R
import com.smtersoyoglu.movieapp.presentation.components.ErrorScreen
import com.smtersoyoglu.movieapp.presentation.components.LoadingBar
import com.smtersoyoglu.movieapp.presentation.search.SearchUiState

@Composable
fun GenreMoviesContent(
    uiState: SearchUiState,
    onMovieClick: (Int) -> Unit,
    onRetry: () -> Unit
) {
    when {
        uiState.isLoading -> {
            LoadingBar()
        }

        uiState.error != null -> {
            ErrorScreen(
                message = stringResource(R.string.error_message, uiState.error),
                onRetry = onRetry
            )
        }

        else -> {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(21.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.padding(top = 12.dp)
            ) {
                items(
                    items = uiState.genreMovies,
                    key = { it.id }
                ) { movie ->
                    MovieGridItem(
                        movie = movie,
                        onMovieClick = onMovieClick
                    )
                }
            }
        }
    }
}