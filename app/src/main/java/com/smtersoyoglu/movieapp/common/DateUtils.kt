package com.smtersoyoglu.movieapp.common

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun String?.formatDate(
    fromPattern: String = "yyyy-MM-dd",
    toPattern: String = "dd MMM yyyy",
    displayLocale: Locale = Locale.getDefault()
): String {
    if (this.isNullOrBlank()) return "Bilinmiyor"

    return try {
        val parser = SimpleDateFormat(fromPattern, Locale.ROOT)
        val date = parser.parse(this)

        val formatter = SimpleDateFormat(toPattern, displayLocale)
        date?.let { formatter.format(it) } ?: "Geçersiz Tarih"
    } catch (_: ParseException) {
        "Geçersiz Tarih"
    }
}