package com.github.zsoltk.backtrack.composable

import androidx.compose.Composable
import androidx.compose.ambient
import androidx.compose.memo
import androidx.compose.onDispose
import androidx.compose.unaryPlus
import com.github.zsoltk.backtrack.helper.HandlerList
import com.github.zsoltk.backtrack.helper.backPressHandler
import com.github.zsoltk.backtrack.helper.BackStack

@Composable
fun <T> BackHandler(routing: T, children: @Composable() (BackStack<T>) -> Unit) {
    val upstream = +ambient(backPressHandler)
    val downstream = +memo { HandlerList() }

    val backStack = BackStack(routing)
    val handleBackPressHere: () -> Boolean = { downstream.handle() || backStack.pop() }
    upstream.handlers.add(handleBackPressHere)
    +onDispose { upstream.handlers.remove(handleBackPressHere) }

    backPressHandler.Provider(value = downstream) {
        children(backStack)
    }
}
