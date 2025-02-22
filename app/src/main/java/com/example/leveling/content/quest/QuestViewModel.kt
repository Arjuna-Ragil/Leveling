package com.example.leveling.content.quest

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.intl.Locale
import java.text.SimpleDateFormat
import java.util.Date

fun getCurrentDate(): String {
    val sdf = SimpleDateFormat("EEE, dd/MM/yyyy", java.util.Locale.getDefault())
    return sdf.format(Date())
}

fun getCurrentTime(): String {
    val sdf = SimpleDateFormat("HH:mm:ss", java.util.Locale.getDefault())
    return sdf.format(Date())
}