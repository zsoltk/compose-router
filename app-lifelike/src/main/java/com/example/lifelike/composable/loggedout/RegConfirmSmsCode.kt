package com.example.lifelike.composable.loggedout

import androidx.compose.Composable
import androidx.compose.state
import androidx.ui.foundation.TextField
import androidx.ui.input.TextFieldValue
import androidx.ui.text.TextRange
import com.example.lifelike.composable.loggedout.common.RegFlowPanel


interface RegConfirmSmsCode {
    companion object {

        @Composable
        fun Content(onNext: () -> Unit) {
            val code = state {
                val initialValue = "0000"
                TextFieldValue(initialValue, TextRange(initialValue.length, initialValue.length) )
            }

            RegFlowPanel(
                "Confirm SMS code that will never arrive",
                { if (code.value.text.length == 4) onNext() }) {
                TextField(
                    value = code.value,
                    onValueChange = {
                        val digits = it.text.filter { it.isDigit() }
                        code.value = TextFieldValue(
                                if (digits.length < 4) digits else digits.substring(0, 4),
                                TextRange(digits.length, digits.length))
                    }
                )
            }
        }
    }
}
