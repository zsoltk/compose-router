package com.github.zsoltk.compose.savedinstancestate

import android.os.Bundle
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf

private val rootSavedInstanceState = Bundle()

val LocalSavedInstanceState: ProvidableCompositionLocal<Bundle> = compositionLocalOf { rootSavedInstanceState }

@Deprecated(
        message = "Replaced with LocalSavedInstanceState to reflect Compose API changes",
        replaceWith = ReplaceWith("LocalSavedInstanceState")
)
val AmbientSavedInstanceState = LocalSavedInstanceState

internal const val BUNDLE_KEY = "LocalSavedInstanceState"

fun Bundle.saveLocal() {
    putBundle(BUNDLE_KEY, rootSavedInstanceState)
}

@Deprecated(
        message = "Replaced with saveLocal to reflect Compose API changes",
        replaceWith = ReplaceWith("saveLocal()")
)
fun Bundle.saveAmbient() {
    this.saveLocal()
}


