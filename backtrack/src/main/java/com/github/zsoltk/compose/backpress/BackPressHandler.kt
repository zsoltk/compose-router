package com.github.zsoltk.compose.backpress

import androidx.compose.Ambient
import androidx.compose.Composable

internal val backPressHandler: Ambient<BackPressHandler> =
    Ambient.of { throw IllegalStateException("backPressHandler is not initialized") }


class BackPressHandler(
    val id: String = "Root"
) {
    var children = mutableListOf<() -> Boolean>()

    fun handle(): Boolean =
        children.reversed().any { it() }

    @Composable
    fun Provider(children: @Composable() () -> Unit) {
        backPressHandler.Provider(value = this) {
            children()
        }
    }
}

