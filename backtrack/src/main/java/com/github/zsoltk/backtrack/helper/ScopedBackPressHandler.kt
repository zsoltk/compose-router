package com.github.zsoltk.backtrack.helper

import androidx.compose.Ambient

class ScopedBackPressHandler(
    var backStack: BackStack<*>? = null
) {
    var children = mutableListOf<() -> Boolean>()

    fun handle(): Boolean =
        children.reversed().any { it() }
}

internal val backPressHandler: Ambient<ScopedBackPressHandler> =
    Ambient.of { throw IllegalStateException("backPressHandler is not initialized") }


