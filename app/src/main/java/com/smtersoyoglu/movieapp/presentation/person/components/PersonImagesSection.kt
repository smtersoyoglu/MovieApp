package com.smtersoyoglu.movieapp.presentation.person.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.smtersoyoglu.movieapp.R
import coil.compose.AsyncImage
import com.smtersoyoglu.movieapp.domain.model.person.PersonImage
import com.smtersoyoglu.movieapp.presentation.theme.HorizontalDividerColor

@Composable
fun PersonImagesSection(
    images: List<PersonImage>,
    onImageClick: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = stringResource(R.string.photos),
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(images.sortedByDescending { it.voteAverage }) { image ->
                Column(
                    modifier = Modifier.width(150.dp)
                ) {
                    AsyncImage(
                        model = "https://image.tmdb.org/t/p/w500${image.filePath}",
                        contentDescription = "Person Image",
                        modifier = Modifier
                            .size(150.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .border(
                                1.dp,
                                Color(0xFFFFC107).copy(alpha = 0.2f),
                                RoundedCornerShape(8.dp)
                            )
                            .clickable { onImageClick(image.filePath) },
                        contentScale = ContentScale.Crop,
                        error = painterResource(R.drawable.ic_no_image_person),
                        fallback = painterResource(R.drawable.ic_no_image_person)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 4.dp),
                        thickness = 1.dp,
                        color = HorizontalDividerColor
                    )
                }
            }
        }
    }
}
