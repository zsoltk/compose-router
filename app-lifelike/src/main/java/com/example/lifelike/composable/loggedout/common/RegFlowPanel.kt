package com.example.lifelike.composable.loggedout.common

import androidx.compose.Composable
import androidx.compose.unaryPlus
import androidx.ui.core.Alignment
import androidx.ui.core.Text
import androidx.ui.core.dp
import androidx.ui.layout.*
import androidx.ui.material.MaterialTheme
import com.example.lifelike.composable.common.BigButton

@Composable
fun RegFlowPanel(
    title: String,
    onNext: () -> Unit,
    content: @Composable() () -> Unit = {}
) {
    Column(modifier = Spacing(40.dp), arrangement = Arrangement.SpaceAround) {
        Text(
            text = title,
            style = (+MaterialTheme.typography()).h5
        )
        content()
        Container(
            expanded = true,
            constraints = DpConstraints(maxHeight = 48.dp),
            alignment = Alignment.CenterRight
        ) {
            BigButton(
                text = "Next",
                onClick = onNext
            )
        }
    }
}
