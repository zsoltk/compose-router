package com.example.lifelike.composable.loggedout.common

import androidx.compose.Composable
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.foundation.Box
import androidx.ui.foundation.Text
import androidx.ui.layout.*
import androidx.ui.material.MaterialTheme
import androidx.ui.unit.dp
import com.example.lifelike.composable.common.BigButton

@Composable
fun RegFlowPanel(
    title: String,
    onNext: () -> Unit,
    content: @Composable() () -> Unit = {}
) {
    Column(modifier = Modifier.padding(40.dp), arrangement = Arrangement.SpaceAround) {
        Text(
            text = title,
            style = MaterialTheme.typography.h5
        )
        content()
        Box(
            modifier = Modifier.fillMaxSize()
                    + Modifier.preferredSizeIn(DpConstraints(maxHeight = 48.dp))
                    + Modifier.wrapContentSize(Alignment.CenterEnd)
        ) {
            BigButton(
                text = "Next",
                onClick = onNext
            )
        }
    }
}
