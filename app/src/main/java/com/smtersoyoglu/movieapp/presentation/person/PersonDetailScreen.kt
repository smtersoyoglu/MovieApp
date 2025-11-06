package com.smtersoyoglu.movieapp.presentation.person

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.smtersoyoglu.movieapp.R
import com.smtersoyoglu.movieapp.presentation.components.ErrorScreen
import com.smtersoyoglu.movieapp.presentation.components.LoadingBar
import com.smtersoyoglu.movieapp.presentation.components.PersonImageDialog
import com.smtersoyoglu.movieapp.presentation.person.components.PersonBiography
import com.smtersoyoglu.movieapp.presentation.person.components.PersonHeader
import com.smtersoyoglu.movieapp.presentation.person.components.PersonImagesSection
import com.smtersoyoglu.movieapp.presentation.person.components.PersonMoviesSection

@Composable
fun PersonDetailScreen(
    viewModel: PersonDetailViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
    navigateToMovieDetail: (Int) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var selectedPersonImageUrl by remember { mutableStateOf<String?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        when {
            uiState.isLoading -> {
                LoadingBar()
            }

            uiState.error != null -> {
                ErrorScreen(message = uiState.error ?: stringResource(R.string.error_message))
            }

            else -> {
                uiState.personDetails?.let { personDetails ->
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        item {
                            PersonHeader(
                                person = personDetails,
                                externalIds = uiState.personExternalIds,
                            )


                        }
                        item {
                            PersonBiography(
                                biography = personDetails.biography,
                                isExpanded = uiState.isBiographyExpanded,
                                onToggleExpand = { viewModel.toggleBiography() }
                            )
                        }
                        item {
                            uiState.personImages.takeIf { it.isNotEmpty() }?.let { images ->
                                PersonImagesSection(
                                    images = images,
                                    onImageClick = { imageUrl ->
                                        selectedPersonImageUrl = imageUrl
                                    })
                            }
                        }
                        item {
                            uiState.personMovieCredits?.let { credits ->
                                PersonMoviesSection(
                                    title = stringResource(R.string.movies_acted_in),
                                    movies = credits.cast.filter { it.character?.isNotEmpty() == true },
                                    onMovieClick = navigateToMovieDetail
                                )
                            }
                        }
                    }
                    selectedPersonImageUrl?.let { url ->
                        PersonImageDialog(
                            imageUrl = url,
                            onDismiss = { selectedPersonImageUrl = null })
                    }
                }
            }
        }
        IconButton(
            onClick = navigateBack,
            modifier = Modifier
                .padding(top = 46.dp, start = 16.dp)
                .align(Alignment.TopStart)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.8f))
        ) {
            Icon(
                modifier = Modifier.size(28.dp),
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}