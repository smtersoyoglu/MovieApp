package com.smtersoyoglu.movieapp.presentation.detail.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.smtersoyoglu.movieapp.R
import com.smtersoyoglu.movieapp.domain.model.movie.MovieImage

@Composable
fun MovieImagesSection(
    images: List<MovieImage>?,
    onImageClick: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = stringResource(R.string.images),
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(6.dp))
        if (images.isNullOrEmpty()) {
            Text(
                text = stringResource(R.string.no_images_available),
                color = Color.Gray,
                style = MaterialTheme.typography.bodyMedium,
            )
        } else {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(images) { image ->
                    AsyncImage(
                        model = "https://image.tmdb.org/t/p/w500${image.filePath}",
                        contentDescription = "Movie backdrop",
                        modifier = Modifier
                            .size(width = 200.dp, height = 120.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .border(
                                1.dp,
                                Color(0xFFFFC107).copy(alpha = 0.2f),
                                RoundedCornerShape(8.dp)
                            )
                            .clickable { onImageClick(image.filePath) },
                        error = painterResource(R.drawable.ic_image_not_found),
                        fallback = painterResource(R.drawable.ic_image_not_found)
                    )
                }
            }
        }
    }
}