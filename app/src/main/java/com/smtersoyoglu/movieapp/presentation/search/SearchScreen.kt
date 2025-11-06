package com.smtersoyoglu.movieapp.presentation.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.smtersoyoglu.movieapp.R
import com.smtersoyoglu.movieapp.presentation.search.components.GenreChipsRow
import com.smtersoyoglu.movieapp.presentation.search.components.GenreMoviesContent
import com.smtersoyoglu.movieapp.presentation.search.components.SearchMoviesContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    navigateToDetail: (Int) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val searchMoviesPagingItems = viewModel.searchMoviesPaging.collectAsLazyPagingItems()
    var searchQuery by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(horizontal = 12.dp)
    ) {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(R.string.search_title),
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Black,
                titleContentColor = Color.White
            )
        )
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { newQuery ->
                searchQuery = newQuery
                viewModel.updateSearchQuery(newQuery)
            },
            label = {
                Text(
                    stringResource(R.string.search_movies),
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White
                )
            },
            textStyle = TextStyle(color = Color.White, fontSize = 16.sp),
            leadingIcon = {
                Icon(
                    Icons.Default.Search,
                    contentDescription = null,
                    tint = Color.White
                )
            },
            shape = RoundedCornerShape(32.dp),
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFFFFC107).copy(alpha = 0.2f),
                unfocusedBorderColor = Color(0xFFFFC107).copy(alpha = 0.2f),
                cursorColor = Color.White,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            )
        )

        if (searchQuery.isBlank()) {
            GenreChipsRow(
                genres = uiState.genres,
                selectedGenreId = uiState.selectedGenreId,
                onGenreClick = { viewModel.selectGenre(it) }
            )

            GenreMoviesContent(
                uiState = uiState,
                onMovieClick = navigateToDetail,
                onRetry = { viewModel.retry() }
            )
        } else {
            SearchMoviesContent(
                pagingItems = searchMoviesPagingItems,
                searchQuery = searchQuery,
                onMovieClick = navigateToDetail,
                onRetry = { viewModel.retry() }
            )
        }
    }
}