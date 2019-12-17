package com.example.poormansbackstack.backpress.composable

import androidx.compose.Composable
import androidx.compose.State
import androidx.compose.ambient
import androidx.compose.memo
import androidx.compose.onDispose
import androidx.compose.state
import androidx.compose.unaryPlus
import com.example.poormansbackstack.backstack.BackStack
import com.example.poormansbackstack.backpress.BackPressHandlers
import com.example.poormansbackstack.backpress.backPressHandler

@Composable
fun <T> BackHandler(routing: T, children: @Composable() (State<BackStack<T>>) -> Unit) {
    val backStackState = +state {
        BackStack(
            routing
        )
    }
    val parentHandler = +ambient(backPressHandler)
    val childrenHandler = +memo { BackPressHandlers() }
    val handleBackStack = {
        if (!childrenHandler.handleBackPress()) {
            var backStack by backStackState
            if (backStack.size > 1) {
                println("Popping backstack with size ${backStack.size}")
                backStack = backStack.pop()
                true
            } else {
                false
            }
        } else {
            true
        }
    }

    parentHandler.handlers.add(handleBackStack)
    +onDispose { parentHandler.handlers.remove(handleBackStack) }

    backPressHandler.Provider(value = childrenHandler) {
        children(backStackState)
    }
}
