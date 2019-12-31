package com.github.zsoltk.backtrack.composable

import android.os.Bundle
import androidx.compose.Composable
import androidx.compose.ambient
import androidx.compose.memo
import androidx.compose.onCommit
import androidx.compose.unaryPlus
import com.github.zsoltk.backtrack.helper.ScopedBackPressHandler
import com.github.zsoltk.backtrack.helper.backPressHandler
import com.github.zsoltk.backtrack.helper.BackStack
import java.io.Serializable

private const val KEY_BACK_STACK = "backStack"

private fun keyForRouting(masterKey: String, indexOfRoutingInBackStack: Int) =
    "$masterKey.$indexOfRoutingInBackStack"

/**
 * Adds back stack functionality with bubbling up fallbacks if the back stack cannot be popped
 * on this level.
 *
 * @param defaultRouting   The default routing to initialise the back stack with
 * @param children  The @Composable to wrap with this BackHandler. It will have access to the back stack.
 */
@Composable
fun <T : Serializable> BackHandler(contextId: String, defaultRouting: T, children: @Composable() (BackStack<T>) -> Unit) {
    val upstreamBundle = +ambient(savedInstanceState)
    val upstreamHandler = +ambient(backPressHandler)
    val localHandler = +memo { ScopedBackPressHandler("${upstreamHandler.id}.$contextId") }
    val localBundle = fetchLocalBundle(upstreamBundle, localHandler.id)
    val backStack = fetchBackStack(localBundle, localHandler.id, defaultRouting)
    val handleBackPressHere: () -> Boolean = { localHandler.handle() || backStack.pop() }

    +onCommit {
        upstreamHandler.children.add(handleBackPressHere)
        onDispose { upstreamHandler.children.remove(handleBackPressHere) }
    }

    backPressHandler.Provider(value = localHandler) {
        BundleScope(backStack, localBundle, localHandler.id, children)
    }
}

private fun fetchLocalBundle(upstreamBundle: Bundle, id: String): Bundle =
    upstreamBundle.getBundle(id) ?: Bundle().also {
        upstreamBundle.putBundle(id, it)
    }

private fun <T : Serializable> fetchBackStack(bundle: Bundle, masterKey: String, defaultRouting: T): BackStack<T> {
    // Only storing elements, not BackStack itself (had issues with @Model + Serializable)
    val restored = bundle.getSerializable(KEY_BACK_STACK) as ArrayList<T>?
    val elements = restored ?: arrayListOf(defaultRouting)

    return BackStack(elements).apply { onElementRemoved = { indexOfRemovedElement ->
        val key = keyForRouting(masterKey, indexOfRemovedElement)
        bundle.remove(key)
    }}
}

@Composable
private fun <T : Serializable> BundleScope(
    backStack: BackStack<T>,
    bundle: Bundle,
    masterKey: String,
    children: @Composable() (BackStack<T>) -> Unit
) {
    bundle.putSerializable(KEY_BACK_STACK, backStack.elements)
    val key = keyForRouting(masterKey, backStack.lastIndex)
    val restored = bundle.getBundle(key)
    val bundleForChildren = restored ?: Bundle().also { bundle.putBundle(key, it) }

    savedInstanceState.Provider(value = bundleForChildren) {
        children(backStack)
    }
}
