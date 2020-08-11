package com.example.lifelike.composable.loggedin

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
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
