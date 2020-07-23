package com.example.lifelike.composable.loggedout

import androidx.compose.Composable
import androidx.ui.foundation.TextField
import androidx.ui.input.TextFieldValue
import androidx.ui.text.TextRange
import androidx.ui.tooling.preview.Preview
import com.example.lifelike.composable.loggedout.common.RegFlowPanel
import com.example.lifelike.entity.User


interface RegUserName {
    companion object {

        @Composable
        fun Content(user: User, onNext: () -> Unit) {
            RegFlowPanel("Your fake name", onNext) {
                TextField(
                    value = TextFieldValue(user.name, TextRange(user.name.length,user.name.length)),
                    onValueChange = { user.name = it.text }
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewRegUserName() {
    RegUserName.Content(User("Test user", "123456"), {})
}
