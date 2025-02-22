package com.example.leveling.content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.leveling.ui.theme.background

@Composable
fun Profile() {
    Surface(
        color = Color.Transparent,
        modifier = Modifier
            .fillMaxSize()
            .then(background())
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(text = "profile")
        }
    }
}