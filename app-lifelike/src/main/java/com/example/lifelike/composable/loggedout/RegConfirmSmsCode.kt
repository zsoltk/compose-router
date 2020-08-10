package com.example.lifelike.composable.loggedout

import androidx.compose.foundation.BaseTextField
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.state
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import com.example.lifelike.composable.loggedout.common.RegFlowPanel


interface RegConfirmSmsCode {
    companion object {

        @ExperimentalFoundationApi
        @Composable
        fun Content(onNext: () -> Unit) {
            val code = state {
                val initialValue = "0000"
                TextFieldValue(initialValue, TextRange(initialValue.length, initialValue.length) )
            }

            RegFlowPanel(
                "Confirm SMS code that will never arrive",
                { if (code.value.text.length == 4) onNext() }) {
                BaseTextField(
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
