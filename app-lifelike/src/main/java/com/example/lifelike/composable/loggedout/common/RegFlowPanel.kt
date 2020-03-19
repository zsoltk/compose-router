package com.example.lifelike.composable.loggedout.common

import androidx.compose.Composable
import androidx.ui.core.Alignment
import androidx.ui.core.Text
import androidx.ui.layout.Arrangement
import androidx.ui.layout.Column
import androidx.ui.layout.Container
import androidx.ui.layout.DpConstraints
import androidx.ui.layout.LayoutPadding
import androidx.ui.material.MaterialTheme
import androidx.ui.unit.dp
import com.example.lifelike.composable.common.BigButton

@Composable
fun RegFlowPanel(
    title: String,
    onNext: () -> Unit,
    content: @Composable() () -> Unit = {}
) {
    Column(modifier = LayoutPadding(40.dp), arrangement = Arrangement.SpaceAround) {
        Text(
            text = title,
            style = MaterialTheme.typography().h5
        )
        content()
        Container(
            expanded = true,
            constraints = DpConstraints(maxHeight = 48.dp),
            alignment = Alignment.CenterEnd
        ) {
            BigButton(
                text = "Next",
                onClick = onNext
            )
        }
    }
}
