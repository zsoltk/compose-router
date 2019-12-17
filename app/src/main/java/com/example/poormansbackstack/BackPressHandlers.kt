package com.example.poormansbackstack

class BackPressHandlers {
    var handlers = mutableListOf<BackPressHandler>()

    fun handleBackClick(): Boolean = handlers.reversed().any { it() }
}

typealias BackPressHandler = () -> Boolean
