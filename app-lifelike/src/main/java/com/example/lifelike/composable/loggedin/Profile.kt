package com.example.lifelike.composable.loggedin

import androidx.compose.Composable
import androidx.compose.unaryPlus
import androidx.ui.core.Text
import androidx.ui.core.dp
import androidx.ui.layout.*
import androidx.ui.material.Button
import androidx.ui.material.MaterialTheme
import androidx.ui.tooling.preview.Preview
import com.example.lifelike.entity.User


interface Profile {

    companion object {
        @Composable
        fun Content(user: User, onLogout: () -> Unit) {
            Column(modifier = Spacing(40.dp), arrangement = Arrangement.SpaceAround) {
                Text(
                    text = "You are logged in as:",
                    style = (+MaterialTheme.typography()).h4
                )
                Text(
                    text = user.name,
                    style = (+MaterialTheme.typography()).h5
                )
                Button(onClick = onLogout) {
                    Text(text = "Log out")
                }
            }
        }
    }
}