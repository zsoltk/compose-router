package com.example.lifelike.composable.loggedout

import androidx.compose.foundation.Box
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.DpConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredSizeIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.MaterialTheme
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
                        .preferredSizeIn(DpConstraints(maxHeight = 48.dp))
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
