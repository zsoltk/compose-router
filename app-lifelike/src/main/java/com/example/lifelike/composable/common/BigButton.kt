package com.example.lifelike.composable.common

import androidx.compose.Composable
import androidx.compose.unaryPlus
import androidx.ui.material.Button
import androidx.ui.material.ContainedButtonStyle
import androidx.ui.material.MaterialTheme

@Composable
fun BigButton(text: String, onClick: () -> Unit) {
    Button(
        text = text.toUpperCase(),
        style = ContainedButtonStyle().copy(textStyle = (+MaterialTheme.typography()).body1),
        onClick = onClick
    )
}