package com.github.zsoltk.backtrack.composable

import androidx.compose.Composable
import androidx.compose.memo
import androidx.compose.onDispose
import androidx.compose.unaryPlus
import com.github.zsoltk.backtrack.helper.ScopedBackPressHandler
import com.github.zsoltk.backtrack.helper.backPressHandler

/**
 * Interfaces between integration point (Activity) and [BackHandler] Composables. This should be the
 * outermost layer
 *
 * @param rootHandler   An instance of [ScopedBackPressHandler] that should be created in the scope of the
 *                       integration point (Activity). The initial trigger of [HandlerList.handle]
 *                       should probably come from onBackPressed() in your Activity.
 * @param children      The @Composable to wrap with this BackHandler. This might be the root element
 *                       in your Composable tree.
 */
@Composable
fun RootBackHandler(rootHandler: ScopedBackPressHandler, children: @Composable() () -> Unit) {
    val downstream = +memo { ScopedBackPressHandler() }
    val handleBackPressHere: () -> Boolean = { downstream.handle() }
    rootHandler.children.add(handleBackPressHere)
    +onDispose { rootHandler.children.remove(handleBackPressHere) }

    backPressHandler.Provider(value = downstream) {
        children()
    }
}
