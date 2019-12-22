package com.example.lifelike.composable.loggedout

import androidx.compose.Composable
import androidx.ui.core.TextField
import com.example.lifelike.composable.loggedout.common.RegFlowPanel
import com.example.lifelike.entity.User


interface RegUserName {
    companion object {

        @Composable
        fun Content(user: User, onNext: () -> Unit) {
            RegFlowPanel("Your fake name", onNext) {
                TextField(
                    value = user.name,
                    onValueChange = { user.name = it }
                )
            }
        }
    }
}
