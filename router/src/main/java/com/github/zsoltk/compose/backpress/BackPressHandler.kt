package com.github.zsoltk.compose.backpress

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf

val LocalBackPressHandler: ProvidableCompositionLocal<BackPressHandler> =
        compositionLocalOf { throw IllegalStateException("backPressHandler is not initialized") }

@Deprecated(
        message = "Replaced with LocalBackPressHandler to reflect Compose API changes",
        replaceWith = ReplaceWith("LocalBackPressHandler")
)
val AmbientBackPressHandler = LocalBackPressHandler


class BackPressHandler(
    val id: String = "Root"
) {
    var children = mutableListOf<() -> Boolean>()

    fun handle(): Boolean =
        children.reversed().any { it() }
}

