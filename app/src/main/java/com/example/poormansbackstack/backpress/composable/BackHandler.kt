package com.example.poormansbackstack.backpress.composable

import androidx.compose.Composable
import androidx.compose.ambient
import androidx.compose.memo
import androidx.compose.onDispose
import androidx.compose.unaryPlus
import com.example.poormansbackstack.backpress.HandlerList
import com.example.poormansbackstack.backpress.backPressHandler
import com.example.poormansbackstack.backstack.BackStack

@Composable
fun <T> BackHandler(routing: T, children: @Composable() (BackStack<T>) -> Unit) {
    val upstream = +ambient(backPressHandler)
    val downstream = +memo { HandlerList() }

    val backStack =  BackStack(routing)
    val handleBackPressHere: () -> Boolean = { downstream.handle() || backStack.pop() }
    upstream.handlers.add(handleBackPressHere)
    +onDispose { upstream.handlers.remove(handleBackPressHere) }

    backPressHandler.Provider(value = downstream) {
        children(backStack)
    }
}
