package com.example.leveling.ui.theme

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush

@Composable
fun loginBackground(): Modifier {
    return Modifier.background(
        brush = Brush.verticalGradient(
            colors = listOf(MaterialTheme.colorScheme.surface, MaterialTheme.colorScheme.surfaceVariant)
        )
    )
}

@Composable
fun background(): Modifier{
    return Modifier.background(
        brush = Brush.verticalGradient(
            colors = listOf(MaterialTheme.colorScheme.surface, MaterialTheme.colorScheme.background)
        )
    )
}