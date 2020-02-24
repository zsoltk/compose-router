package com.github.zsoltk.compose.savedinstancestate

import android.os.Bundle
import androidx.compose.ProvidableAmbient
import androidx.compose.ambientOf

val AmbientTimeCapsule: ProvidableAmbient<TimeCapsule> = ambientOf {
    TimeCapsule(null)
}

class TimeCapsule(
    savedInstanceState: Bundle?
) {
    private var savedInstanceState: Bundle = savedInstanceState?.getBundle(KEY) ?: Bundle()

    fun onSaveInstanceState(outState: Bundle) {
        outState.putBundle(KEY, savedInstanceState)
    }

    companion object {
        private const val KEY = "TimeCapsule.Root"
    }
}
