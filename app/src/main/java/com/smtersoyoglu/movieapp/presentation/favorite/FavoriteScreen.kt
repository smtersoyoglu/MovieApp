package com.smtersoyoglu.movieapp.presentation.favorite

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.smtersoyoglu.movieapp.R
import com.smtersoyoglu.movieapp.domain.model.FavoriteMovie
import com.smtersoyoglu.movieapp.navigation.Screen
import com.smtersoyoglu.movieapp.presentation.components.EmptyScreen
import com.smtersoyoglu.movieapp.presentation.components.ErrorScreen
import com.smtersoyoglu.movieapp.presentation.components.LoadingBar
import kotlinx.coroutines.delay

@Composable
fun FavoriteScreen(
    viewModel: FavoriteViewModel = hiltViewModel(),
    navController: NavController,
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.message) {
        uiState.message?.let { message ->
            snackbarHostState.showSnackbar(message = message)
            delay(2000)
            viewModel.clearMessage()
        }
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Black)
        .padding(top = 56.dp)) {
        when {
            uiState.isLoading -> {
                LoadingBar()
            }

            uiState.error != null -> {
                ErrorScreen(
                    message = uiState.error ?: "Bilinmeyen bir hata oluştu",
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            uiState.favorites.isEmpty() -> {
               EmptyScreen(
                   message = "No favorites yet",
                   modifier = Modifier.align(Alignment.Center)
               )
            }

            else -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(uiState.favorites) { favorite ->
                        FavoriteGridItem(
                            favorite = favorite,
                            onMovieClick = { navController.navigate(Screen.Detail(favorite.id)) },
                            onRemoveClick = { viewModel.removeFavorite(favorite) }
                        )
                    }
                }
            }
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}


@Composable
fun FavoriteGridItem(
    favorite: FavoriteMovie,
    onMovieClick: () -> Unit,
    onRemoveClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .clickable { onMovieClick() },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Black)
    ) {
        Box {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w500${favorite.posterPath}",
                contentDescription = favorite.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(2 / 3f),
                error = painterResource(R.drawable.ic_image_not_found),
                fallback = painterResource(R.drawable.ic_image_not_found)
            )

            IconButton(
                onClick = onRemoveClick,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(4.dp)
                    .size(24.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Remove from favorites",
                    tint = Color.White,
                    modifier = Modifier.size(21.dp)
                )
            }
            Row(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .background(Color.Black.copy(alpha = 0.5f))
                    .padding(8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = favorite.title,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}