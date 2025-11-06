package com.smtersoyoglu.movieapp.presentation.detail.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.smtersoyoglu.movieapp.R

@Composable
fun CastItem(
    name: String,
    character: String,
    profilePath: String?,
    personId: Int,
    onCastClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(end = 8.dp)
            .clickable { onCastClick(personId) },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = "https://image.tmdb.org/t/p/w500$profilePath",
            contentDescription = name,
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(12.dp))
                .border(
                    1.dp,
                    Color(0xFFFFC107).copy(alpha = 0.2f),
                    RoundedCornerShape(12.dp)
                ),
            contentScale = ContentScale.Crop,
            error = painterResource(R.drawable.ic_no_image_person),
            fallback = painterResource(R.drawable.ic_no_image_person)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = name,
            style = MaterialTheme.typography.labelSmall.copy(fontSize = 12.sp),
            color = Color.White,
            maxLines = 1,
            textAlign = TextAlign.Center
        )
        Text(
            text = character,
            style = MaterialTheme.typography.labelSmall.copy(fontSize = 12.sp),
            maxLines = 1,
            color = Color.White.copy(alpha = 0.6f),
            textAlign = TextAlign.Center
        )
    }
} 