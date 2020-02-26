package com.github.zsoltk.compose.savedinstancestate

import android.os.Bundle
import androidx.compose.ProvidableAmbient
import androidx.compose.ambientOf

private val rootSavedInstanceState = Bundle()

val AmbientSavedInstanceState: ProvidableAmbient<Bundle> =
    ambientOf { rootSavedInstanceState }

internal const val BUNDLE_KEY = "AmbientSavedInstanceState"

fun Bundle.saveAmbient() {
    putBundle(BUNDLE_KEY, rootSavedInstanceState)
}


