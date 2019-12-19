package com.github.zsoltk.backtrack.helper

import androidx.compose.Ambient

class HandlerList(
    var backStack: BackStack<*>? = null
) {
    var handlers = mutableListOf<() -> Boolean>()

    fun handle(): Boolean =
        handlers.reversed().any { it() }
}

internal val backPressHandler: Ambient<HandlerList> =
    Ambient.of { throw IllegalStateException("Handler is not initialized") }


