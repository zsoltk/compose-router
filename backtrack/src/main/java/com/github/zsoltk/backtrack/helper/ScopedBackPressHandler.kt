package com.github.zsoltk.backtrack.helper

import androidx.compose.Ambient
import androidx.compose.Composable

internal val backPressHandler: Ambient<ScopedBackPressHandler> =
    Ambient.of { throw IllegalStateException("backPressHandler is not initialized") }


class ScopedBackPressHandler(
    val id: String = "Root"
) {
    var children = mutableListOf<() -> Boolean>()

    fun handle(): Boolean =
        children.reversed().any { it() }

    @Composable
    fun Provide(children: @Composable() () -> Unit) {
        backPressHandler.Provider(value = this) {
            children()
        }
    }
}

