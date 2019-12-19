package com.example.lifelike.composable.loggedout

import androidx.compose.Composable
import androidx.compose.Effect
import androidx.compose.unaryPlus
import androidx.ui.core.Alignment
import androidx.ui.core.Text
import androidx.ui.core.dp
import androidx.ui.core.sp
import androidx.ui.graphics.Color
import androidx.ui.layout.*
import androidx.ui.material.*
import androidx.ui.material.surface.Surface
import androidx.ui.text.ParagraphStyle
import androidx.ui.text.style.TextAlign
import com.example.lifelike.composable.common.BigButton


interface Splash {
    companion object {

        @Composable
        fun Content(onNext: () -> Unit) {
            Column(modifier = Spacing(40.dp), arrangement = Arrangement.SpaceAround) {
                Text(
                    text = "Welcome to amazing fake app",
                    style = (+MaterialTheme.typography()).h4,
                    paragraphStyle = ParagraphStyle(
                        textAlign = TextAlign.Center
                    )
                )
                Container(
                    expanded = true,
                    constraints = DpConstraints(maxHeight = 48.dp),
                    alignment = Alignment.Center
                ) {
                    BigButton(
                        text = "Create account",
                        onClick = onNext
                    )
                }
            }
        }
    }
}
