package com.example.lifelike.composable.loggedout

import androidx.compose.Composable
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.foundation.Box
import androidx.ui.foundation.Text
import androidx.ui.layout.*
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
            Column(modifier = Modifier.padding(40.dp), arrangement = Arrangement.SpaceAround) {
                Text(
                    text = AnnotatedString(
                        text = "Welcome to amazing fake app",
                        paragraphStyle = ParagraphStyle(
                            textAlign = TextAlign.Center
                        )
                    ),
                    style = MaterialTheme.typography.h4
                )
                Box(
                    modifier = Modifier.fillMaxSize()
                            + Modifier.preferredSizeIn(DpConstraints(maxHeight = 48.dp))
                            + Modifier.wrapContentSize(Alignment.Center)
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
