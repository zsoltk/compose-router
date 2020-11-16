package com.example.lifelike.composable.loggedout.common

import androidx.compose.foundation.Box
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredSizeIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.MaterialTheme
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
                .preferredSizeIn(maxHeight = 48.dp)
                .wrapContentSize(Alignment.CenterEnd)
        ) {
            BigButton(
                text = "Next",
                onClick = onNext
            )
        }
    }
}
