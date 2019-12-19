package com.example.lifelike.composable.loggedout

import androidx.compose.Composable
import androidx.compose.state
import androidx.compose.unaryPlus
import androidx.ui.core.Alignment
import androidx.ui.core.Text
import androidx.ui.core.TextField
import androidx.ui.layout.Column
import androidx.ui.layout.Container
import androidx.ui.material.Button
import com.example.lifelike.entity.User


interface RegFinal {
    companion object {

        @Composable
        fun Content(onNext: () -> Unit) {
            Column {
                Text(text = "Welcome on board!")
                Button(
                    text = "Next",
                    onClick = { onNext() }
                )
            }
        }
    }
}
