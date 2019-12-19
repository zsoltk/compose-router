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


interface RegUserName {
    companion object {

        @Composable
        fun Content(user: User, onNext: () -> Unit) {
            Column {
                Text(text = "Your name: ")
                TextField(
                    value = user.name,
                    onValueChange = { user.name = it }
                )
                Button(
                    text = "Next",
                    onClick = { onNext() }
                )
            }
        }
    }
}
