package com.example.lifelike.composable.common

import androidx.compose.Composable
import androidx.ui.core.Text
import androidx.ui.material.Button
import androidx.ui.material.ContainedButtonStyle
import androidx.ui.material.MaterialTheme

@Composable
fun BigButton(text: String, onClick: () -> Unit) {
    Button(
        style = ContainedButtonStyle(),
        onClick = onClick
    ) {
        Text(
            text = text.toUpperCase(),
            style = MaterialTheme.typography().body1
        )
    }
}
