package com.example.lifelike.composable.common

import androidx.compose.Composable
import androidx.ui.foundation.Text
import androidx.ui.material.Button
import androidx.ui.material.MaterialTheme
import java.util.*

@Composable
fun BigButton(text: String, onClick: () -> Unit) {
    Button(onClick = onClick) {
        Text(
            text = text.toUpperCase(Locale.ROOT),
            style = MaterialTheme.typography.body1
        )
    }
}
