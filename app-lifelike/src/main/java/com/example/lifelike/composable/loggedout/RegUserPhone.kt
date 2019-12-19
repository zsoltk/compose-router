package com.example.lifelike.composable.loggedout

import androidx.compose.Composable
import androidx.ui.core.Text
import androidx.ui.core.TextField
import androidx.ui.layout.Column
import androidx.ui.material.Button
import com.example.lifelike.entity.User


interface RegUserPhone {
    companion object {

        @Composable
        fun Content(user: User, onNext: () -> Unit) {
            Column {
                Text(text = "Your phone number: ")
                TextField(
                    value = user.phone,
                    onValueChange = { user.phone = it.filter { it.isDigit() } }
                )
                Button(
                    text = "Next",
                    onClick = { onNext() }
                )
            }
        }
    }
}
