package com.example.lifelike.composable.loggedout

import androidx.compose.Composable
import androidx.ui.core.Text
import androidx.ui.layout.Column
import androidx.ui.material.Button


interface Splash {
    companion object {

        @Composable
        fun Content(onNext: () -> Unit) {
            Column {
                Text(text = "Welcome to amazing fake app")
                Button(
                    text = "Create account",
                    onClick = { onNext() }
                )
            }
        }
    }
}
