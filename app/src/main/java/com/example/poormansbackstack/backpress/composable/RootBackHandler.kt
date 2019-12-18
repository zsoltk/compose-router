package com.example.poormansbackstack.backpress.composable

import androidx.compose.Composable
import androidx.compose.memo
import androidx.compose.onDispose
import androidx.compose.unaryPlus
import com.example.poormansbackstack.backpress.HandlerList
import com.example.poormansbackstack.backpress.backPressHandler

@Composable
fun RootBackHandler(rootHandler: HandlerList, children: @Composable() () -> Unit) {
    val downstream = +memo { HandlerList() }
    val handleBackPressHere: () -> Boolean = { downstream.handle() }
    rootHandler.handlers.add(handleBackPressHere)
    +onDispose { rootHandler.handlers.remove(handleBackPressHere) }

    backPressHandler.Provider(value = downstream) {
        children()
    }
}
