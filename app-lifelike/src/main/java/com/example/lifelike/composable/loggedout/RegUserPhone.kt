package com.example.lifelike.composable.loggedout

import androidx.compose.Composable
import androidx.ui.core.TextField
import com.example.lifelike.composable.loggedout.common.RegFlowPanel
import com.example.lifelike.entity.User


interface RegUserPhone {
    companion object {

        @Composable
        fun Content(user: User, onNext: () -> Unit) {
            RegFlowPanel("Your fake phone number", onNext) {
                TextField(
                    value = user.phone,
                    onValueChange = { user.phone = it.filter { it.isDigit() } }
                )
            }
        }
    }
}
