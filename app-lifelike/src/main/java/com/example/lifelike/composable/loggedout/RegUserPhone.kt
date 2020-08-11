package com.example.lifelike.composable.loggedout

import androidx.compose.foundation.BaseTextField
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import com.example.lifelike.composable.loggedout.common.RegFlowPanel
import com.example.lifelike.entity.User


interface RegUserPhone {
    companion object {
        @ExperimentalFoundationApi
        @Composable
        fun Content(user: User, onNext: () -> Unit) {
            RegFlowPanel("Your fake phone number", onNext) {
                BaseTextField(
                    value = TextFieldValue(user.phone, TextRange(user.phone.length,user.phone.length)),
                    onValueChange = { user.phone = it.text.filter { it.isDigit() } }
                )
            }
        }
    }
}
