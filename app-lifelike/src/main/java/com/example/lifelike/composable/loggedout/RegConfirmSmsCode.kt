package com.example.lifelike.composable.loggedout

import androidx.compose.Composable
import androidx.compose.state
import androidx.ui.core.TextField
import com.example.lifelike.composable.loggedout.common.RegFlowPanel


interface RegConfirmSmsCode {
    companion object {

        @Composable
        fun Content(onNext: () -> Unit) {
            var code by state { "0000" }

            RegFlowPanel("Confirm SMS code that will never arrive", { if (code.length == 4) onNext() }) {
                TextField(
                    value = code,
                    onValueChange = {
                        val digits = it.filter { it.isDigit() }
                        code = if (digits.length < 4) digits else digits.substring(0, 4) }
                )
            }
        }
    }
}
