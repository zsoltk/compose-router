package com.example.lifelike.composable.loggedin

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lifelike.entity.User


interface Profile {

    companion object {
        @Composable
        fun Content(user: User, onLogout: () -> Unit) {
            Column(
                modifier = Modifier.fillMaxHeight().padding(40.dp),
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    text = "You are logged in as:",
                    style = MaterialTheme.typography.h4
                )
                Column {
                    Text(
                        text = user.name,
                        style = MaterialTheme.typography.h5
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = user.phone,
                        style = MaterialTheme.typography.h5
                    )
                }
                Column {
                    Button(onClick = onLogout) {
                        Text(text = "Log out")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ProfilePreview() {
    Profile.Content(user = User("Demo user", "123456789"), onLogout = {})
}
