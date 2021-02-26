package com.example.lifelike.composable.loggedout.common

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.lifelike.composable.common.BigButton

@Composable
fun RegFlowPanel(
    title: String,
    onNext: () -> Unit,
    content: @Composable () -> Unit = {}
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(40.dp),
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Box(modifier = Modifier.wrapContentSize()) {
            Text(
                text = title,
                style = MaterialTheme.typography.h5
            )
        }
        Box(modifier = Modifier.fillMaxSize().weight(1f).wrapContentHeight(Alignment.CenterVertically)) {
            content()
        }
        Box(
            modifier = Modifier
                .sizeIn(maxHeight = 48.dp)
                .wrapContentSize(Alignment.CenterEnd)
        ) {
            BigButton(
                text = "Next",
                onClick = onNext
            )
        }
    }
}
