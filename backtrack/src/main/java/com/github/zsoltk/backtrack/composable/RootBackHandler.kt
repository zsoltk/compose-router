package com.github.zsoltk.backtrack.composable

import androidx.compose.Composable
import androidx.compose.memo
import androidx.compose.onDispose
import androidx.compose.unaryPlus
import com.github.zsoltk.backtrack.helper.HandlerList
import com.github.zsoltk.backtrack.helper.backPressHandler

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
