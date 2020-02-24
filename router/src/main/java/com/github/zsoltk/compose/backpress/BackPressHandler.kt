package com.github.zsoltk.compose.backpress

import androidx.compose.ProvidableAmbient
import androidx.compose.ambientOf

val AmbientBackPressHandler: ProvidableAmbient<BackPressHandler> =
    ambientOf { throw IllegalStateException("backPressHandler is not initialized") }


class BackPressHandler(
    val id: String = "Root"
) {
    var children = mutableListOf<() -> Boolean>()

    fun handle(): Boolean =
        children.reversed().any { it() }
}

