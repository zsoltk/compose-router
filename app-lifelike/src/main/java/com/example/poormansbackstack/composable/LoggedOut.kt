package com.example.poormansbackstack.composable

import androidx.compose.Composable
import androidx.compose.state
import androidx.compose.unaryPlus
import androidx.ui.core.Text
import androidx.ui.core.TextField
import androidx.ui.layout.Column
import androidx.ui.material.Button
import com.example.poormansbackstack.entity.User

interface LoggedOut {

    companion object {
        @Composable
        fun Content(onLoggedIn: (User) -> Unit) {
            var userName by +state { "Demo user" }

            Column {
                Text(text = "You are logged out")
                Text(text = "User name: ")
                TextField(
                    value = userName,
                    onValueChange = { userName = it }
                )
                Button(
                    text = "Login",
                    onClick = {
                        val user = User(userName)
                        onLoggedIn.invoke(user)
                    }
                )
            }
        }
    }
}
