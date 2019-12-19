package com.example.lifelike.composable.loggedin

import androidx.compose.Composable
import androidx.ui.core.Alignment
import androidx.ui.core.Text
import androidx.ui.layout.Container


interface Hello {
    companion object {

        @Composable
        fun Content() {
            Container(expanded = true, alignment = Alignment.Center) {
                Text(text = "Hello Compose!")
            }
        }
    }
}
