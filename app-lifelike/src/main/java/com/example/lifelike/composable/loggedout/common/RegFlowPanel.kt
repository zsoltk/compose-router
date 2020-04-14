package com.example.lifelike.composable.loggedout.common

import androidx.compose.Composable
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.foundation.Box
import androidx.ui.foundation.Text
import androidx.ui.layout.Arrangement
import androidx.ui.layout.Column
import androidx.ui.layout.DpConstraints
import androidx.ui.layout.fillMaxSize
import androidx.ui.layout.padding
import androidx.ui.layout.preferredSizeIn
import androidx.ui.layout.wrapContentHeight
import androidx.ui.layout.wrapContentSize
import androidx.ui.material.MaterialTheme
import androidx.ui.unit.dp
import com.example.lifelike.composable.common.BigButton

@Composable
fun RegFlowPanel(
    title: String,
    onNext: () -> Unit,
    content: @Composable() () -> Unit = {}
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(40.dp),
        arrangement = Arrangement.SpaceAround
    ) {
        Box(modifier = Modifier.wrapContentSize()) {
            Text(
                text = title,
                style = MaterialTheme.typography.h5
            )
        }
        Box(modifier = Modifier.fillMaxSize().weight(1f).wrapContentHeight()) {
            content()
        }
        Box(
            modifier = Modifier
                .preferredSizeIn(DpConstraints(maxHeight = 48.dp))
                .wrapContentSize(Alignment.CenterEnd)
        ) {
            BigButton(
                text = "Next",
                onClick = onNext
            )
        }
    }
}
