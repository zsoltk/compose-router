package com.github.zsoltk.compose.router.transient

import androidx.compose.Ambient
import androidx.compose.Composable
import androidx.compose.ambient
import androidx.compose.memo
import androidx.compose.onCommit
import androidx.compose.unaryPlus
import com.github.zsoltk.compose.backpress.BackPressHandler
import com.github.zsoltk.compose.backpress.backPressHandler
import com.github.zsoltk.compose.savedinstancestate.BundleScope
import com.github.zsoltk.compose.savedinstancestate.savedInstanceState

private fun key(backStackIndex: Int) =
    "K$backStackIndex"

private val backStackMap = Ambient.of<MutableMap<Any, BackStack<*>>> {
    mutableMapOf()
}

/**
 * Adds back stack functionality with bubbling up fallbacks if the back stack cannot be popped
 * on this level.
 *
 * @param defaultRouting   The default routing to initialise the back stack with
 * @param children  The @Composable to wrap with this BackHandler. It will have access to the back stack.
 */
@Composable
fun <T> BackHandler(contextId: String, defaultRouting: T, children: @Composable() (BackStack<T>) -> Unit) {
    val upstreamHandler = +ambient(backPressHandler)
    val localHandler = +memo { BackPressHandler("${upstreamHandler.id}.$contextId") }
    val backStack = fetchBackStack(localHandler.id, defaultRouting)
    val handleBackPressHere: () -> Boolean = { localHandler.handle() || backStack.pop() }

    +onCommit {
        upstreamHandler.children.add(handleBackPressHere)
        onDispose { upstreamHandler.children.remove(handleBackPressHere) }
    }

    BundleScope(key(backStack.lastIndex), autoDispose = false) {
        backPressHandler.Provider(value = localHandler) {
            children(backStack)
        }
    }
}

private fun <T> fetchBackStack(key: String, defaultRouting: T): BackStack<T> {
    val upstreamBundle = +ambient(savedInstanceState)
    val onElementRemoved: (Int) -> Unit = { upstreamBundle.remove(key(it)) }

    val upstreamBackStacks = +ambient(backStackMap)
    val existing = upstreamBackStacks[key] as BackStack<T>?

    return existing ?: BackStack(defaultRouting, onElementRemoved).also {
        upstreamBackStacks[key] = it
    }
}

