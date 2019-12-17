package com.example.poormansbackstack.backpress.composable

import androidx.compose.Composable
import androidx.compose.memo
import androidx.compose.unaryPlus
import com.example.poormansbackstack.backpress.BackPress
import com.example.poormansbackstack.backpress.HandlerList
import com.example.poormansbackstack.backpress.backPressHandler

@Composable
fun RootBackHandler(backPress: BackPress, cantPopBackStack: () -> Unit, children: @Composable() () -> Unit) {
    val downstream = +memo { HandlerList() }
    backPressHandler.Provider(value = downstream) {
        children()
    }

    if (backPress.triggered) {
        if (!downstream.handle()) {
            cantPopBackStack()
        }
        backPress.triggered = false
    }
}
