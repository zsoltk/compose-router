package com.example.poormansbackstack.backpress

import androidx.compose.Ambient

typealias BackPressHandler = () -> Boolean

class BackPressHandlers {
    var handlers = mutableListOf<BackPressHandler>()

    fun handleBackPress(): Boolean =
        handlers.reversed().any { it() }
}

val backPressHandler: Ambient<BackPressHandlers> =
    Ambient.of { throw IllegalStateException("Handler is not initialized") }


