package com.example.lifelike.composable.loggedout

import androidx.compose.Composable
import androidx.compose.state
import androidx.compose.unaryPlus
import androidx.ui.core.Alignment
import androidx.ui.core.Text
import androidx.ui.core.TextField
import androidx.ui.core.dp
import androidx.ui.graphics.Color
import androidx.ui.layout.*
import androidx.ui.material.Button
import androidx.ui.material.MaterialTheme
import androidx.ui.material.surface.Surface
import androidx.ui.text.ParagraphStyle
import androidx.ui.text.style.TextAlign
import com.example.lifelike.composable.common.BigButton
import com.example.lifelike.composable.loggedout.common.RegFlowPanel
import com.example.lifelike.entity.User


interface RegUserName {
    companion object {

        @Composable
        fun Content(user: User, onNext: () -> Unit) {
            RegFlowPanel("Your name", onNext) {
                TextField(
                    value = user.name,
                    onValueChange = { user.name = it }
                )
            }
        }
    }
}
