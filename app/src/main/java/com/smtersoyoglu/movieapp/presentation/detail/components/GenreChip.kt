package com.smtersoyoglu.movieapp.presentation.detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smtersoyoglu.movieapp.presentation.theme.HorizontalDividerColor

@Composable
fun GenreChip(genre: String) {
    Box(
        modifier = Modifier
            .border(
                width = 0.5.dp,
                color = HorizontalDividerColor,
                shape = RoundedCornerShape(16.dp)
            )
            .background(
                color = Color.White.copy(alpha = 0.2f),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Text(
            text = genre,
            color = Color.White,
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 15.sp)
        )
    }
} 