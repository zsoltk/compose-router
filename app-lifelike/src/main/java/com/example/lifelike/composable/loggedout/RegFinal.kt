package com.example.lifelike.composable.loggedout

import androidx.compose.runtime.Composable
import com.example.lifelike.composable.loggedout.common.RegFlowPanel


interface RegFinal {
    companion object {

        @Composable
        fun Content(onNext: () -> Unit) {
            RegFlowPanel("Welcome on board!", onNext)
        }
    }
}
