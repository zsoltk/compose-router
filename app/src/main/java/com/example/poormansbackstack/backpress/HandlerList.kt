package com.example.poormansbackstack.backpress

import androidx.compose.Ambient

class HandlerList {
    var handlers = mutableListOf<() -> Boolean>()

    fun handle(): Boolean =
        handlers.reversed().any { it() }
}

val backPressHandler: Ambient<HandlerList> =
    Ambient.of { throw IllegalStateException("Handler is not initialized") }


