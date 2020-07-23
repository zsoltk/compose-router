package com.github.zsoltk.compose.router

import androidx.compose.Composable
import androidx.compose.ProvidableAmbient
import androidx.compose.Providers
import androidx.compose.ambientOf
import androidx.compose.onCommit
import androidx.compose.remember
import com.github.zsoltk.compose.backpress.AmbientBackPressHandler
import com.github.zsoltk.compose.backpress.BackPressHandler
import com.github.zsoltk.compose.savedinstancestate.AmbientSavedInstanceState
import com.github.zsoltk.compose.savedinstancestate.BundleScope

private fun key(backStackIndex: Int) =
    "K$backStackIndex"

private val backStackMap: MutableMap<Any, BackStack<*>> =
    mutableMapOf()

/**
 * Currently only used for deep link based Routing.
 *
 * Can be set to store a list of Routing elements of different types.
 * The idea is that when we walk through this list in sequence - provided that the sequence
 * is correct - we can set the app into any state that is a combination of Routing on different levels.
 *
 * See [com.example.lifelike.DeepLinkKt.parseProfileDeepLink] in :app-lifelike module for usage
 * example.
 */
val AmbientRouting: ProvidableAmbient<List<Any>> = ambientOf {
    listOf<Any>()
}

/**
 * Adds back stack functionality with bubbling up fallbacks if the back stack cannot be popped
 * on this level.
 *
 * @param defaultRouting    The default routing to initialise the back stack with
 * @param children          The @Composable to wrap with this BackHandler. It will have access to the back stack.
 */
@Composable
inline fun <reified T> Router(
    defaultRouting: T,
    noinline children: @Composable (BackStack<T>) -> Unit
) {
    Router(T::class.java.name, defaultRouting, children)
}

@Composable
fun <T> Router(
    contextId: String,
    defaultRouting: T,
    children: @Composable (BackStack<T>) -> Unit
) {
    val route = AmbientRouting.current
    val routingFromAmbient = route.firstOrNull() as? T
    val downStreamRoute = if (route.size > 1) route.takeLast(route.size - 1) else emptyList()

    val upstreamHandler = AmbientBackPressHandler.current
    val localHandler = remember { BackPressHandler("${upstreamHandler.id}.$contextId") }
    val backStack = fetchBackStack(localHandler.id, defaultRouting, routingFromAmbient)
    val handleBackPressHere: () -> Boolean = { localHandler.handle() || backStack.pop() }

    onCommit {
        upstreamHandler.children.add(handleBackPressHere)
        onDispose { upstreamHandler.children.remove(handleBackPressHere) }
    }

    @Composable
    fun Observe(body: @Composable () -> Unit) = body()

    Observe {
        // Not recomposing router on backstack operation
        BundleScope(key(backStack.lastIndex), autoDispose = false) {
            Providers(
                AmbientBackPressHandler provides localHandler,
                AmbientRouting provides downStreamRoute
            ) {
                children(backStack)
            }
        }
    }
}

@Composable
private fun <T> fetchBackStack(key: String, defaultElement: T, override: T?): BackStack<T> {
    val upstreamBundle = AmbientSavedInstanceState.current
    val onElementRemoved: (Int) -> Unit = { upstreamBundle.remove(key(it)) }

    val existing = backStackMap[key] as BackStack<T>?
    return when {
        override != null -> BackStack(override as T, onElementRemoved)
        existing != null -> existing
        else -> BackStack(defaultElement, onElementRemoved)
    }.also {
        backStackMap[key] = it
    }
}

