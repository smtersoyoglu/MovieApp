package com.smtersoyoglu.movieapp.presentation.components

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalUriHandler

@Composable
fun ExternalLinkIconButton(
    url: String,
    iconPainter: Painter,
    contentDescription: String
) {
    if (url.isNotEmpty()) {
        val uriHandler = LocalUriHandler.current
        IconButton(onClick = { uriHandler.openUri(url) }) {
            Icon(
                painter = iconPainter,
                contentDescription = contentDescription,
                tint = Color.White
            )
        }
    }
}