package com.example.poormansbackstack.backpress.composable

import androidx.compose.Composable
import androidx.compose.ambient
import androidx.compose.memo
import androidx.compose.onDispose
import androidx.compose.unaryPlus
import com.example.poormansbackstack.backpress.BackPressHandlers
import com.example.poormansbackstack.backpress.backPressHandler
import com.example.poormansbackstack.backstack.BackStack

@Composable
fun <T> BackHandler(routing: T, children: @Composable() (BackStack<T>) -> Unit) {
    val backStack =  BackStack(routing)
    val parentHandler = +ambient(backPressHandler)
    val childrenHandler = +memo { BackPressHandlers() }

    val handleBackStack = {
        if (!childrenHandler.handleBackPress()) {
            backStack.pop()
        } else {
            true
        }
    }

    parentHandler.handlers.add(handleBackStack)
    +onDispose { parentHandler.handlers.remove(handleBackStack) }

    backPressHandler.Provider(value = childrenHandler) {
        children(backStack)
    }
}
