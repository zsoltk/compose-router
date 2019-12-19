package com.example.lifelike.composable.loggedout

import androidx.compose.Composable
import androidx.compose.unaryPlus
import androidx.ui.core.Alignment
import androidx.ui.core.Text
import androidx.ui.core.TextField
import androidx.ui.core.dp
import androidx.ui.layout.*
import androidx.ui.material.Button
import androidx.ui.material.MaterialTheme
import androidx.ui.text.ParagraphStyle
import androidx.ui.text.style.TextAlign
import com.example.lifelike.composable.common.BigButton
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
