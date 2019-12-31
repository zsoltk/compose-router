package com.github.zsoltk.backtrack.composable

import android.os.Bundle
import androidx.compose.Composable
import androidx.compose.ambient
import androidx.compose.memo
import androidx.compose.onCommit
import androidx.compose.unaryPlus
import com.github.zsoltk.backtrack.helper.BackStack
import com.github.zsoltk.compose.backpress.BackPressHandler
import com.github.zsoltk.compose.backpress.backPressHandler
import com.github.zsoltk.compose.savedinstancestate.BundleScope
import java.io.Serializable

private const val KEY_BACK_STACK = "backStack"

private fun key(backStackIndex: Int) =
    "K$backStackIndex"

/**
 * Adds back stack functionality with bubbling up fallbacks if the back stack cannot be popped
 * on this level.
 *
 * @param defaultRouting   The default routing to initialise the back stack with
 * @param children  The @Composable to wrap with this BackHandler. It will have access to the back stack.
 */
@Composable
fun <T : Serializable> BackHandler(contextId: String, defaultRouting: T, children: @Composable() (BackStack<T>) -> Unit) {
    val upstreamHandler = +ambient(backPressHandler)
    val localHandler = +memo { BackPressHandler("${upstreamHandler.id}.$contextId") }

    BundleScope(localHandler.id, autoDispose = false) { routerBundle ->
        val backStack = fetchBackStack(routerBundle, defaultRouting)
        val handleBackPressHere: () -> Boolean = { localHandler.handle() || backStack.pop() }

        +onCommit {
            upstreamHandler.children.add(handleBackPressHere)
            onDispose { upstreamHandler.children.remove(handleBackPressHere) }
        }

        BackStackPersistence(backStack, routerBundle) {
            BundleScope(key(backStack.lastIndex), autoDispose = false) {
                backPressHandler.Provider(value = localHandler) {
                    children(backStack)
                }
            }
        }
    }
}

private fun <T : Serializable> fetchBackStack(
    bundle: Bundle,
    defaultRouting: T
): BackStack<T> {
    // Only storing elements, not BackStack itself
    val restored = bundle.getSerializable(KEY_BACK_STACK) as ArrayList<T>?
    val elements = restored ?: arrayListOf(defaultRouting)

    return BackStack(elements) { indexOfRemovedElement ->
        // Whenever a child is popped / replaced, its scoped Bundle needs to be removed
        bundle.remove(key(indexOfRemovedElement))
    }
}

/**
 * Since BackStack<T> is @Model, this block will recompose every time the back stack is
 * manipulated. We use this to save back stack in savedInstanceState.
 */
@Composable
private fun <T : Serializable> BackStackPersistence(
    backStack: BackStack<T>,
    bundle: Bundle,
    children: @Composable() (BackStack<T>) -> Unit
) {
    bundle.putSerializable(KEY_BACK_STACK, backStack.elements)
    children(backStack)
}
