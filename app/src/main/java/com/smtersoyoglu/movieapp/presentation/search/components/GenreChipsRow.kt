package com.smtersoyoglu.movieapp.presentation.search.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.smtersoyoglu.movieapp.domain.model.movie.Genre
import com.smtersoyoglu.movieapp.presentation.theme.HorizontalDividerColor

@Composable
fun GenreChipsRow(
    genres: List<Genre>,
    selectedGenreId: Int?,
    onGenreClick: (Int) -> Unit
) {
    LazyRow(
        contentPadding = PaddingValues(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(genres) { genre ->
            val isSelected = selectedGenreId == genre.id
            AssistChip(
                onClick = { onGenreClick(genre.id) },
                label = {
                    Text(
                        text = genre.name,
                        color = if (isSelected) Color.Black else Color.White,
                        style = MaterialTheme.typography.bodyLarge
                    )
                },
                colors = AssistChipDefaults.assistChipColors(
                    containerColor = if (isSelected) Color.White else Color.Transparent,
                    labelColor = if (isSelected) Color.Black else Color.White
                ),
                border = BorderStroke(
                    width = 0.5.dp,
                    color = if (isSelected) Color.White else HorizontalDividerColor
                )
            )
        }
    }
}