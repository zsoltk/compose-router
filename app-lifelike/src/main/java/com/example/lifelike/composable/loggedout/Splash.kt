package com.example.lifelike.composable.loggedout

import androidx.compose.Composable
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.foundation.Box
import androidx.ui.foundation.Text
import androidx.ui.layout.Arrangement
import androidx.ui.layout.Column
import androidx.ui.layout.DpConstraints
import androidx.ui.layout.fillMaxWidth
import androidx.ui.layout.padding
import androidx.ui.layout.preferredSizeIn
import androidx.ui.layout.wrapContentSize
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
