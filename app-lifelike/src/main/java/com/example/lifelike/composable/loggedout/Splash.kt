package com.example.lifelike.composable.loggedout

import androidx.compose.Composable
import androidx.ui.core.Alignment
import androidx.ui.core.Text
import androidx.ui.layout.Arrangement
import androidx.ui.layout.Column
import androidx.ui.layout.Container
import androidx.ui.layout.DpConstraints
import androidx.ui.layout.LayoutPadding
import androidx.ui.material.MaterialTheme
import androidx.ui.text.AnnotatedString
import androidx.ui.text.ParagraphStyle
import androidx.ui.text.style.TextAlign
import androidx.ui.unit.dp
import com.example.lifelike.composable.common.BigButton


interface Splash {
    companion object {

        @Composable
        fun Content(onNext: () -> Unit) {
            Column(modifier = LayoutPadding(40.dp), arrangement = Arrangement.SpaceAround) {
                Text(
                    text = AnnotatedString(
                        text = "Welcome to amazing fake app",
                        paragraphStyle = ParagraphStyle(
                            textAlign = TextAlign.Center
                        )
                    ),
                    style = MaterialTheme.typography().h4
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
