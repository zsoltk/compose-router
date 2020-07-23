package com.example.lifelike.composable.loggedout

import androidx.compose.Composable
import androidx.ui.foundation.TextField
import androidx.ui.input.TextFieldValue
import androidx.ui.text.TextRange
import com.example.lifelike.composable.loggedout.common.RegFlowPanel
import com.example.lifelike.entity.User


interface RegUserPhone {
    companion object {
        @Composable
        fun Content(user: User, onNext: () -> Unit) {
            RegFlowPanel("Your fake phone number", onNext) {
                TextField(
                    value = TextFieldValue(user.phone, TextRange(user.phone.length,user.phone.length)),
                    onValueChange = { user.phone = it.text.filter { it.isDigit() } }
                )
            }
        }
    }
}
