package com.example.poormansbackstack.composable

import androidx.compose.Composable
import androidx.compose.unaryPlus
import androidx.ui.core.Alignment
import androidx.ui.core.Text
import androidx.ui.layout.Column
import androidx.ui.layout.Container
import androidx.ui.material.Button
import androidx.ui.material.MaterialTheme
import com.example.poormansbackstack.entity.User


interface Profile {

    companion object {
        @Composable
        fun Content(user: User, onLogout: () -> Unit) {
            Container(alignment = Alignment.Center) {
                Column {
                    Text(
                        text = "You are logged in as: ${user.name}",
                        style = (+MaterialTheme.typography()).h4
                    )
                    Button(onClick = onLogout) {
                        Text(text = "Log out")
                    }
                }
            }
        }
    }
}
