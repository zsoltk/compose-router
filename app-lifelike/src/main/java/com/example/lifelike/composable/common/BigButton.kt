package com.example.lifelike.composable.common

import androidx.compose.foundation.Text
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun BigButton(text: String, onClick: () -> Unit) {
    Button(onClick = onClick) {
        Text(
            text = text.toUpperCase(),
            style = MaterialTheme.typography.body1.copy(color = Color.White)
        )
    }
}
