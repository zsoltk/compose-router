package com.github.zsoltk.backtrack.composable

import androidx.compose.Composable
import androidx.compose.ambient
import androidx.compose.memo
import androidx.compose.onDispose
import androidx.compose.unaryPlus
import com.github.zsoltk.backtrack.helper.ScopedBackPressHandler
import com.github.zsoltk.backtrack.helper.backPressHandler
import com.github.zsoltk.backtrack.helper.BackStack

/**
 * Adds back stack functionality with bubbling up fallbacks if the back stack cannot be popped
 * on this level.
 *
 * @param id        A unique identifier for this child BackHandler in the scope of its parent BackHandler
 * @param routing   The default routing to initialise the back stack with
 * @param children  The @Composable to wrap with this BackHandler. It will have access to the back stack.
 */
@Composable
fun <T> BackHandler(id: Any, routing: T, children: @Composable() (BackStack<T>) -> Unit) {
    val upstream = +ambient(backPressHandler)
    val saveRestorePoint = upstream.backStack?.lastEntry()?.children
    val backStack = saveRestorePoint?.get(id) ?: BackStack(routing).also {
        saveRestorePoint?.set(id, it)
    }
    val downstream = +memo { ScopedBackPressHandler(backStack) }

    val handleBackPressHere: () -> Boolean = { downstream.handle() || backStack.pop() }
    upstream.children.add(handleBackPressHere)
    +onDispose { upstream.children.remove(handleBackPressHere) }

    backPressHandler.Provider(value = downstream) {
        children(backStack as BackStack<T>)
    }
}
