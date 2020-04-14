package com.example.lifelike.composable.loggedin

import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.foundation.Text
import androidx.ui.graphics.Color
import androidx.ui.layout.Arrangement
import androidx.ui.layout.Column
import androidx.ui.layout.Spacer
import androidx.ui.layout.fillMaxHeight
import androidx.ui.layout.fillMaxWidth
import androidx.ui.layout.padding
import androidx.ui.layout.preferredHeight
import androidx.ui.material.Button
import androidx.ui.material.MaterialTheme
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import com.example.lifelike.entity.User


interface Profile {

    companion object {
        @Composable
        fun Content(user: User, onLogout: () -> Unit) {
            Column(
                modifier = Modifier.fillMaxHeight().padding(40.dp),
                arrangement = Arrangement.SpaceAround
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
                    Spacer(modifier = Modifier.preferredHeight(16.dp))
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
