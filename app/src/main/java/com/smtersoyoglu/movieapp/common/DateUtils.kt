package com.smtersoyoglu.movieapp.common

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

fun String?.formatDate(fromFormat: String = "yyyy-MM-dd", toFormat: String = "dd-MM-yyyy"): String {
    if (this == null) return "Bilinmiyor"
    val originalFormat = SimpleDateFormat(fromFormat, Locale.getDefault())
    val targetFormat = SimpleDateFormat(toFormat, Locale.getDefault())
    return try {
        val date = originalFormat.parse(this)
        if (date != null) targetFormat.format(date) else "Geçersiz Tarih"
    } catch (e: ParseException) {
        "Geçersiz Tarih"
    }
}