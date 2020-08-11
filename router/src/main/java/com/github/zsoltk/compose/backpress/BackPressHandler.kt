package com.github.zsoltk.compose.backpress

import androidx.compose.runtime.ProvidableAmbient
import androidx.compose.runtime.ambientOf

val AmbientBackPressHandler: ProvidableAmbient<BackPressHandler> =
    ambientOf { throw IllegalStateException("backPressHandler is not initialized") }


class BackPressHandler(
    val id: String = "Root"
) {
    var children = mutableListOf<() -> Boolean>()

    fun handle(): Boolean =
        children.reversed().any { it() }
}

