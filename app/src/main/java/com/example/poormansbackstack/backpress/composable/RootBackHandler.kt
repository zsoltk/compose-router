package com.example.poormansbackstack.backpress.composable

import androidx.compose.Composable
import androidx.compose.memo
import androidx.compose.unaryPlus
import com.example.poormansbackstack.backpress.BackPress
import com.example.poormansbackstack.backpress.BackPressHandlers
import com.example.poormansbackstack.backpress.backPressHandler

@Composable
fun RootBackHandler(backPress: BackPress, cantPopBackStack: () -> Unit, children: @Composable() () -> Unit) {
    val childrenHandler = +memo { BackPressHandlers() }
    backPressHandler.Provider(value = childrenHandler) {
        children()
    }
    if (backPress.triggered) {
        if (!childrenHandler.handleBackPress()) {
            cantPopBackStack()
        }
        backPress.triggered = false
    }
}
