package com.example.lifelike.composable.loggedout

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.lifelike.composable.common.BigButton


interface Splash {
    companion object {

        @Composable
        fun Content(onNext: () -> Unit) {
            Column(modifier = Modifier.padding(40.dp), verticalArrangement = Arrangement.SpaceAround) {
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
                    modifier = Modifier.fillMaxWidth()
                        .sizeIn(maxHeight = 48.dp)
                        .wrapContentSize(Alignment.Center)
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
